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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

public class LimitedInputStreamTest {

    @Test
    public void testReadByteWithLimitLessThanLength() throws IOException {
        InputStream stream = new ByteArrayInputStream(new byte[] { 1, 2, 3 });
        try (LimitedInputStream limitedStream = new LimitedInputStream(stream, 2)) {
            Assert.assertEquals(1, limitedStream.read());
            Assert.assertEquals(2, limitedStream.read());
            Assert.assertEquals(-1, limitedStream.read());
        }
    }

    @Test
    public void testReadBytesWithLimitLessThanLength() throws IOException {
        InputStream stream = new ByteArrayInputStream(new byte[] { 1, 2, 3, 4 });
        try (LimitedInputStream limitedStream = new LimitedInputStream(stream, 3)) {
            byte[] buffer = new byte[2];
            Assert.assertEquals(2, limitedStream.read(buffer));
            Assert.assertEquals(1, limitedStream.read(buffer));
            Assert.assertEquals(-1, limitedStream.read(buffer));
        }
    }

    @Test
    public void testReadByteWithLimitGreaterThanLength() throws IOException {
        InputStream stream = new ByteArrayInputStream(new byte[] { 1, 2, 3 });
        try (LimitedInputStream limitedStream = new LimitedInputStream(stream, 4)) {
            Assert.assertEquals(1, limitedStream.read());
            Assert.assertEquals(2, limitedStream.read());
            Assert.assertEquals(3, limitedStream.read());
            Assert.assertEquals(-1, limitedStream.read());
        }
    }

    @Test
    public void testReadBytesWithLimitGreaterThanLength() throws IOException {
        InputStream stream = new ByteArrayInputStream(new byte[] { 1, 2, 3 });
        try (LimitedInputStream limitedStream = new LimitedInputStream(stream, 4)) {
            byte[] buffer = new byte[2];
            Assert.assertEquals(2, limitedStream.read(buffer));
            Assert.assertEquals(1, limitedStream.read(buffer));
            Assert.assertEquals(-1, limitedStream.read(buffer));
        }
    }

}
