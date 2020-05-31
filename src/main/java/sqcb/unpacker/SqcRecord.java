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

/**
 * Represents a record about a SQC file contained in a SQC bank.
 *
 * @see SqcBank
 */
public class SqcRecord {

    private final String name;

    private final int offset;

    private final int size;

    /**
     * Constructs a new record.
     *
     * @param name   the name
     * @param offset the offset
     * @param size   the size
     */
    public SqcRecord(String name, int offset, int size) {
        super();
        this.name = name;
        this.offset = offset;
        this.size = size;
    }

    /**
     * Constructs a new record with the fields copied from the specified record.
     *
     * @param record the record
     */
    public SqcRecord(SqcRecord record) {
        this(record.getName(), record.getOffset(), record.getSize());
    }

    /**
     * Returns the file name.
     *
     * @return the file name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the offset.
     *
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Returns the size
     *
     * @return the size
     */
    public int getSize() {
        return size;
    }

}