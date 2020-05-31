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

import java.io.IOException;
import java.io.InputStream;

/**
 * Implements an input stream that limits the number of bytes read.
 */
public class LimitedInputStream extends InputStream {

    private final InputStream stream;

    private int remaining;

    /**
     * Constructs a new input stream with the specified limit.
     *
     * @param stream the input stream
     * @param limit  the limit
     */
    public LimitedInputStream(InputStream stream, int limit) {
        super();
        this.stream = stream;
        this.remaining = limit;
    }

    @Override
    public int read() throws IOException {
        if (remaining > 0) {
            int c = stream.read();
            if (c != -1) {
                remaining--;
            }
            return c;
        } else {
            return -1;
        }
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (remaining > 0) {
            int allowed = Math.min(len, remaining);
            int c = stream.read(b, off, allowed);
            if (c != -1) {
                remaining -= c;
            }
            return c;
        } else {
            return -1;
        }
    }

}
