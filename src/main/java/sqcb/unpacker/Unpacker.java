/* ******************************************************************************
Copyright 2020 Peshek of Rattay

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
****************************************************************************** */
package sqcb.unpacker;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import sqcb.unpacker.io.ExtensionPredicate;

/**
 * Unpacks SQCB files.
 */
public class Unpacker {

    /** Extension of SQCB files (sqcb) */
    public static final String SQCB_EXTENSION = "sqcb";

    /** Default buffer size (16 kB) */
    public static final int DEFAULT_BUFFER_SIZE = 16384;

    private final int bufferSize;

    private final List<UnpackerListener> listeners;

    /**
     * Constructs a new unpacker with the specified buffer size and listeners.
     *
     * @param bufferSize the buffer size
     * @param listeners  the listeners
     */
    public Unpacker(int bufferSize, List<UnpackerListener> listeners) {
        super();
        this.bufferSize = bufferSize;
        this.listeners = listeners;
    }

    /**
     * Constructs a new unpacker with the default buffer size and listeners.
     *
     * @param listeners the listeners
     * @see #DEFAULT_BUFFER_SIZE
     */
    public Unpacker(List<UnpackerListener> listeners) {
        this(DEFAULT_BUFFER_SIZE, listeners);
    }

    /**
     * Constructs a new unpacker with the default buffer.
     *
     * @see #DEFAULT_BUFFER_SIZE
     */
    public Unpacker() {
        this(DEFAULT_BUFFER_SIZE, Collections.emptyList());
    }

    /**
     * Unpacks the specified paths.
     *
     * @param paths the paths
     * @throws IOException if an I/O error occurs
     */
    public void unpackPaths(String... paths) throws IOException {
        for (String pathStr : paths) {
            Path path = Paths.get(pathStr);
            if (Files.isDirectory(path)) {
                unpackDirectory(path);
            } else if (Files.isRegularFile(path)) {
                unpackFile(path);
            }
        }
    }

    /**
     * Unpacks the source directory containing SQCB files.
     *
     * @param srcDirectory the source directory
     * @throws IOException if an I/O error occurs
     */
    public void unpackDirectory(Path srcDirectory) throws IOException {
        ExtensionPredicate sqcbPredicate = new ExtensionPredicate(SQCB_EXTENSION);
        try (Stream<Path> pathStream = Files.walk(srcDirectory)) {
            List<Path> files = pathStream.filter(sqcbPredicate.and(Files::isRegularFile)).collect(Collectors.toList());
            for (Path file : files) {
                unpackFile(file);
            }
        }
    }

    /**
     * Unpacks the source SQCB file.
     *
     * @param srcFile the SQCB file
     * @throws IOException if an I/O error occurs
     */
    public void unpackFile(Path srcFile) throws IOException {
        Path directory = srcFile.getParent();
        notifyBeforeSqcb(srcFile);
        try (InputStream stream = Files.newInputStream(srcFile)) {
            try (BufferedInputStream bufferedStream = new BufferedInputStream(stream)) {
                unpackStream(bufferedStream, directory);
            }
        }
        notifyAfterSqcb(srcFile);
    }

    /**
     * Unpacks a given stream containing a SQCB file into the specified directory.
     *
     * @param stream        the stream containing a SQCB file
     * @param destDirectory the destination directory
     * @throws IOException if an I/O error occurs
     */
    public void unpackStream(InputStream stream, Path destDirectory) throws IOException {
        try (SqcBank bank = new SqcBank(stream)) {
            while (bank.hasNext()) {
                SqcFile sqcFile = bank.next();
                Path file = destDirectory.resolve(sqcFile.getName());
                unpackFile(sqcFile, file);
            }
        }
    }

    /**
     * Unpacks a single SQC file.
     *
     * @param sqcFile  the source SQC file
     * @param destFile the destination file
     * @throws IOException if an I/O error occurs
     */
    public void unpackFile(SqcFile sqcFile, Path destFile) throws IOException {
        notifyBeforeSqc(destFile);
        try (OutputStream out = Files.newOutputStream(destFile)) {
            byte[] block = new byte[bufferSize];
            for (int len; (len = sqcFile.getStream().read(block)) != -1;) {
                out.write(block, 0, len);
            }
        }
        notifyAfterSqc(destFile);
    }

    protected void notifyBeforeSqcb(Path file) {
        for (UnpackerListener listener : listeners) {
            listener.beforeSqcb(file);
        }
    }

    protected void notifyAfterSqcb(Path file) {
        for (UnpackerListener listener : listeners) {
            listener.afterSqcb(file);
        }
    }

    protected void notifyBeforeSqc(Path file) {
        for (UnpackerListener listener : listeners) {
            listener.beforeSqc(file);
        }
    }

    protected void notifyAfterSqc(Path file) {
        for (UnpackerListener listener : listeners) {
            listener.afterSqc(file);
        }
    }

}
