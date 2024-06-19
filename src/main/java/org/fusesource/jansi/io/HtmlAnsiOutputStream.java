/*
 * Copyright (C) 2009-2023 the original author(s).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fusesource.jansi.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.fusesource.jansi.AnsiColors;
import org.fusesource.jansi.AnsiMode;
import org.fusesource.jansi.AnsiType;

/**
 * A optimized/edited {@link HtmlAnsiOutputStream} for Jansi 2.x and above. <br>
 * @author <a href="https://github.com/Osiris-Team">Osiris Team</a>
 * @author <a href="http://code.dblock.org">Daniel Doubrovkine</a>
 */
public class HtmlAnsiOutputStream extends AnsiOutputStream {
    public AnsiToHtmlProcessor processor;

    @Override
    public void close() throws IOException {
        closeAttributes();
        super.close();
    }

    public static final String[] ANSI_COLOR_MAP = {
        "black",
        // Made the colors below less bright, to fit better in pages with a white background.
        "#781914", // red
        "#147823", // green
        "#787814", // yellow
        "#142078", // blue
        "#431478", // magenta
        "#146b78", // cyan
        "white",
    };

    private static final byte[] BYTES_QUOT = "&quot;".getBytes();
    private static final byte[] BYTES_AMP = "&amp;".getBytes();
    private static final byte[] BYTES_LT = "&lt;".getBytes();
    private static final byte[] BYTES_GT = "&gt;".getBytes();

    public HtmlAnsiOutputStream(OutputStream os) {
        super(
                os,
                new WidthSupplier() {
                    @Override
                    public int getTerminalWidth() {
                        return Integer.MAX_VALUE;
                    }
                },
                AnsiMode.Default,
                null,
                AnsiType.Native,
                AnsiColors.TrueColor,
                Charset.defaultCharset(),
                null,
                null,
                true);

        this.processor = new AnsiToHtmlProcessor(os, this);
        this.processor.haos = this;

        super.processor = this.processor;
        super.ap = this.processor;
    }

    private final List<String> closingAttributes = new ArrayList<>();

    public void write(String s) throws IOException {
        super.out.write(s.getBytes());
    }

    public void writeAttribute(String s) throws IOException {
        write("<" + s + ">");
        closingAttributes.add(0, s.split(" ", 2)[0]);
    }

    public void closeAttributes() throws IOException {
        for (String attr : closingAttributes) {
            write("</" + attr + ">");
        }
        closingAttributes.clear();
    }

    public void write(int data) throws IOException {
        switch (data) {
            case 34: // "
                out.write(BYTES_QUOT);
                break;
            case 38: // &
                out.write(BYTES_AMP);
                break;
            case 60: // <
                out.write(BYTES_LT);
                break;
            case 62: // >
                out.write(BYTES_GT);
                break;
            default:
                super.write(data);
        }
    }

    public void writeLine(byte[] buf, int offset, int len) throws IOException {
        write(buf, offset, len);
        closeAttributes();
    }
}
