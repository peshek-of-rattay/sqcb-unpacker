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
package sqcb.unpacker.cli;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import sqcb.unpacker.Unpacker;
import sqcb.unpacker.UnpackerListener;
import sqcb.unpacker.UnpackerLogger;

/**
 * Implements the command line interface (CLI) for SQCB Unpacker.
 */
public class UnpackerCli {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            UnpackerHelp help = new UnpackerHelp(System.out);
            help.print();
            System.exit(1);

        } else {
            UnpackerListener logger = new UnpackerLogger(System.out);
            List<UnpackerListener> listeners = Arrays.asList(logger);
            Unpacker unpacker = new Unpacker(listeners);
            unpacker.unpackPaths(args);
        }
    }

}
