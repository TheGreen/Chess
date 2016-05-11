/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Jan Christian Grünhage; Alex Klug
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Jan Christian Grünhage; Alex Klug
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Jan Christian Grünhage
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.janchristiangruenhage.util.terminal.menu;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * The <tt>Menu</tt> class.
 * Probably not the simplest Terminal Menu Implementation, but it is better than nothing.
 *
 * @author Jan Christian Grünhage @jcgruenhage
 * @version 0.1
 */
public class Menu implements MenuItem {
    private Menu root;
    private Menu parent;
    private String name;
    private String selectPrompt;
    private List<MenuItem> items; //either a submenu or a menu item


    public Menu(String name, String selectPrompt) {
        this.root = this;
        this.parent = this;
        this.name = name;
        this.selectPrompt = selectPrompt;
        this.items = new LinkedList<>();
    }

    public Menu(Menu parent, String name, String selectPrompt) {
        this.root = parent.getRoot();
        this.parent = parent;
        this.name = name;
        this.selectPrompt = selectPrompt;
        this.items = new LinkedList<>();
    }

    public MenuItem select() {
        if (parent != this) {
            add(parent);
        } else {
            if (root != this) {
                add(root);
            }
        }
        System.out.println(selectPrompt);
        for (int i = 1; i <= items.size(); i++) {
            System.out.println(i + ". " + items.get(i - 1).getName());
        }
        Scanner scanner = new Scanner(System.in);
        int position;
        while (true) {
            String input = scanner.next();
            if (input.matches("\\d*")) { //Matches digits only
                position = Integer.parseInt(input) - 1;
                if (position < items.size() && position >= 0) {
                    return items.get(position);
                } else {
                    System.out.println("The index is out of Bounds, try again.");
                }
            } else {
                System.out.println("Input is not a number, try again.");
            }
        }
    }

    public void add(MenuItem item) {
        if (!(item == Item.NULL)) {
            items.add(item);
        }
    }


    @Override
    public String getName() {
        return name;
    }

    public Menu getRoot() {
        return root;
    }
}
