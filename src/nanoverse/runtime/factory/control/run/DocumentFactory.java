/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package nanoverse.runtime.factory.control.run;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;

/**
 * Converts a text input file into a root element.
 * <p>
 * Created by dbborens on 11/23/14.
 */
public abstract class DocumentFactory {

    public static Element instantiate(String projectPath) {
        try {
            File f = new File(projectPath);
            SAXReader reader = new SAXReader();
            Document document = reader.read(f);
            Element root = document.getRootElement();
            return root;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
