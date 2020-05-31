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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import sqcb.unpacker.io.CountingInputStream;
import sqcb.unpacker.io.LimitedInputStream;

/**
 * Represents a SQC bank (SQCB).
 */
public class SqcBank implements AutoCloseable {

    protected static final String HEADER = "SQCB";

    protected static final String VERSION = "1.00";

    private final CountingInputStream stream;

    private List<SqcRecord> records;

    private int currentRecord;

    /**
     * Constructs a new SQC bank from the specified input stream.
     *
     * @param stream the stream
     */
    public SqcBank(InputStream stream) {
        super();
        this.stream = new CountingInputStream(stream);
    }

    /**
     * Tells whether this SQC bank contains a next SQC file.
     *
     * @return {@code true} if there is a next SQL file or {@code false} otherwise
     * @throws IOException if an I/O error occurs
     */
    public boolean hasNext() throws IOException {
        ensureRecordsRead();
        return currentRecord < records.size();
    }

    /**
     * Returns the next SQC file from this SQC bank.
     *
     * @return the next SQC file
     * @throws NoSuchElementException if there is none
     * @throws IOException            if an I/O error occurs
     */
    public SqcFile next() throws IOException {
        ensureRecordsRead();
        if (currentRecord >= records.size()) {
            throw new NoSuchElementException();
        }
        SqcRecord record = records.get(currentRecord);
        currentRecord++;
        if (stream.getBytesRead() != record.getOffset()) {
            throw new IOException("Broken file");
        }
        return new SqcFile(record, new LimitedInputStream(stream, record.getSize()));
    }

    private void ensureRecordsRead() throws IOException {
        if (records == null) {
            readRecords();
        }
    }

    private void readRecords() throws IOException {
        String header = readFixedString(HEADER.length());
        if (!HEADER.equals(header)) {
            throw new IOException("Invalid header");
        }
        String version = readFixedString(VERSION.length());
        if (!VERSION.equals(version)) {
            throw new IOException("Invalid version");
        }
        int number = readInt();
        this.records = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            String recordName = readNullTerminatedString();
            int recordOffset = readInt();
            int recordSize = readInt();
            SqcRecord record = new SqcRecord(recordName, recordOffset, recordSize);
            records.add(record);
        }
    }

    private String readFixedString(int length) throws IOException {
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = readChar();
        }
        return new String(chars);
    }

    private String readNullTerminatedString() throws IOException {
        StringBuilder builder = new StringBuilder();
        char ch = readChar();
        while (ch != 0) {
            builder.append(ch);
            ch = readChar();
        }
        return builder.toString();
    }

    private char readChar() throws IOException {
        byte b1 = readByte();
        byte b2 = readByte();
        return (char) ((b1 & 0xff) | ((b2 & 0xff) << 8));
    }

    private int readInt() throws IOException {
        byte b1 = readByte();
        byte b2 = readByte();
        byte b3 = readByte();
        byte b4 = readByte();
        return (b1 & 0xff) | ((b2 & 0xff) << 8) | ((b3 & 0xff) << 16) | ((b4 & 0xff) << 24);
    }

    private byte readByte() throws IOException {
        int b = this.stream.read();
        if (b == -1) {
            throw new IOException("Unexpected end of stream");
        }
        return (byte) b;
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }

}
