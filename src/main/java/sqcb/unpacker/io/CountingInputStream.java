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
package sqcb.unpacker.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Implements a input stream that counts the number of bytes read.
 */
public class CountingInputStream extends FilterInputStream {

    private int bytesRead;

    /**
     * Constructs a new counting input stream.
     *
     * @param stream the input stream
     */
    public CountingInputStream(InputStream stream) {
        super(stream);
    }

    @Override
    public int read() throws IOException {
        int c = super.read();
        if (c != -1) {
            bytesRead++;
        }
        return c;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int c = super.read(b, off, len);
        if (c != -1) {
            bytesRead += c;
        }
        return c;
    }

    /**
     * Return the number of bytes read.
     *
     * @return the number of bytes read
     */
    public int getBytesRead() {
        return bytesRead;
    }

}
