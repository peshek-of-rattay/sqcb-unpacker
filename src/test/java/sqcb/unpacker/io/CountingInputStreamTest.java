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

public class CountingInputStreamTest {

    @Test
    public void testReadByte() throws IOException {
        InputStream stream = new ByteArrayInputStream(new byte[] { 1, 2 });
        try (CountingInputStream countingStream = new CountingInputStream(stream)) {
            Assert.assertEquals(0, countingStream.getBytesRead());
            Assert.assertEquals(1, countingStream.read());
            Assert.assertEquals(1, countingStream.getBytesRead());
            Assert.assertEquals(2, countingStream.read());
            Assert.assertEquals(2, countingStream.getBytesRead());
            Assert.assertEquals(-1, countingStream.read());
            Assert.assertEquals(2, countingStream.getBytesRead());
        }
    }

    @Test
    public void testReadBytes() throws IOException {
        InputStream stream = new ByteArrayInputStream(new byte[] { 1, 2, 3 });
        try (CountingInputStream countingStream = new CountingInputStream(stream)) {
            byte[] buffer = new byte[2];
            Assert.assertEquals(0, countingStream.getBytesRead());
            Assert.assertEquals(2, countingStream.read(buffer));
            Assert.assertEquals(2, countingStream.getBytesRead());
            Assert.assertEquals(1, countingStream.read(buffer));
            Assert.assertEquals(3, countingStream.getBytesRead());
            Assert.assertEquals(-1, countingStream.read(buffer));
            Assert.assertEquals(3, countingStream.getBytesRead());
        }
    }

}
