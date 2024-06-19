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

public class AnsiToHtmlProcessor extends AnsiProcessor {
    public boolean concealOn = false;
    public HtmlAnsiOutputStream haos;

    AnsiToHtmlProcessor(OutputStream os, HtmlAnsiOutputStream haos) {
        super(os);
        this.haos = haos;
    }

    @Override
    protected void processSetAttribute(int attribute) throws IOException {
        switch (attribute) {
            case ATTRIBUTE_CONCEAL_ON:
                haos.write("\u001B[8m");
                concealOn = true;
                break;
            case ATTRIBUTE_INTENSITY_BOLD:
                haos.writeAttribute("b");
                break;
            case ATTRIBUTE_INTENSITY_NORMAL:
                haos.closeAttributes();
                break;
            case ATTRIBUTE_UNDERLINE:
                haos.writeAttribute("u");
                break;
            case ATTRIBUTE_UNDERLINE_OFF:
                haos.closeAttributes();
                break;
            case ATTRIBUTE_NEGATIVE_ON:
                break;
            case ATTRIBUTE_NEGATIVE_OFF:
                break;
            default:
                break;
        }
    }

    @Override
    protected void processAttributeReset() throws IOException {
        if (concealOn) {
            haos.write("\u001B[0m");
            concealOn = false;
        }
        haos.closeAttributes();
    }

    @Override
    protected void processSetForegroundColor(int color, boolean bright) throws IOException {
        haos.writeAttribute("span style=\"color: " + HtmlAnsiOutputStream.ANSI_COLOR_MAP[color] + ";\"");
    }

    @Override
    protected void processSetBackgroundColor(int color, boolean bright) throws IOException {
        haos.writeAttribute("span style=\"background-color: " + HtmlAnsiOutputStream.ANSI_COLOR_MAP[color] + ";\"");
    }
}
