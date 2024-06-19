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
package org.fusesource.jansi.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.io.HtmlAnsiOutputStream;

public class UtilsAnsiHtml {

    /**
     * Converts the provided {@link Ansi} into a html String. <br>
     * Uses {@link HtmlAnsiOutputStream} to achieve this.
     */
    public String convertAnsiToHtml(Ansi ansi) throws IOException {
        return convertAnsiToHtml(ansi.toString());
    }

    /**
     * Converts the provided ansi String into a html String. <br>
     * Uses {@link HtmlAnsiOutputStream} to achieve this.
     */
    public String convertAnsiToHtml(String ansiString) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try (HtmlAnsiOutputStream hos = new HtmlAnsiOutputStream(os)) {
            hos.write(ansiString.getBytes(StandardCharsets.UTF_8));
        }
        os.close();
        return os.toString();
    }
}
