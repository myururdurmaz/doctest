/*
 * Copyright 2013, devbliss GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.devbliss.doctest.items;

import java.util.List;

public class MenuDocItem implements DocItem {

    private String title;
    private List<LinkDocItem> files;

    public MenuDocItem(String title, List<LinkDocItem> files) {
        this.setTitle(title);
        this.files = files;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<LinkDocItem> getFiles() {
        return files;
    }

    public void setFiles(List<LinkDocItem> files) {
        this.files = files;
    }

    public String getItemName() {
        return "menu";
    }

}
