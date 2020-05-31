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

import java.nio.file.Path;
import java.util.function.Predicate;

/**
 * A predicate that tests that a given path has the specified extension.
 */
public class ExtensionPredicate implements Predicate<Path> {

    private final String suffix;

    /**
     * Constructs a new predicate that tests the specified extension.
     *
     * @param extension the extension
     */
    public ExtensionPredicate(String extension) {
        super();
        this.suffix = "." + extension;
    }

    @Override
    public boolean test(Path path) {
        String name = path.getFileName().toString();
        return name.length() > suffix.length() && name.endsWith(suffix);
    }

}
