/*
 * The MIT License (MIT)
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.test.ast;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.spongepowered.test.util.TestHelper;

import java.io.IOException;

@SuppressWarnings("unused")
public class TernaryTest {

    public void mth_basicTernary(boolean a) {
        int r = a ? 6 : 3;
    }

    @Test
    public void testBasicTernary() throws IOException {
        String insn = TestHelper.getAsString(getClass(), "mth_basicTernary");
        String good = "int r = a ? 6 : 3;";
        assertEquals(good, insn);
    }

    public void mth_moreComplexTernary(boolean a, boolean b, int j) {
        int i = a || b ? 0 - j * 24 + Integer.MAX_VALUE : 1 + j;
    }

    @Test
    public void testMoreComplexTernary() throws IOException {
        String insn = TestHelper.getAsString(getClass(), "mth_moreComplexTernary");
        String good = "int i = a || b ? 0 - j * 24 + Integer.MAX_VALUE : 1 + j;";
        assertEquals(good, insn);
    }

    private int field;

    public void mth_basicTernaryToField(boolean a) {
        this.field = a ? 255 : 0;
    }

    @Test
    public void testBasicTernaryToField() throws IOException {
        String insn = TestHelper.getAsString(getClass(), "mth_basicTernaryToField");
        String good = "this.field = a ? 255 : 0;";
        assertEquals(good, insn);
    }

    public void mth_nestedTernary(boolean a, boolean b) {
        int r = a ? b ? 4 : 5 : 3;
    }

    @Test
    public void testNestedTernary() throws IOException {
        String insn = TestHelper.getAsString(getClass(), "mth_nestedTernary");
        String good = "int r = a ? b ? 4 : 5 : 3;";
        assertEquals(good, insn);
    }

    public void mth_nestedTernary2(boolean a, boolean b) {
        int r = a ? 5 : b ? 4 : 3;
    }

    @Test
    public void testNestedTernary2() throws IOException {
        String insn = TestHelper.getAsString(getClass(), "mth_nestedTernary2");
        String good = "int r = a ? 5 : b ? 4 : 3;";
        assertEquals(good, insn);
    }

    public int mth_returnTernary(boolean a) {
        return a ? 6 : 3;
    }

    @Test
    public void testReturnedTernary() throws IOException {
        String insn = TestHelper.getAsString(getClass(), "mth_returnTernary");
        String good = "return a ? 6 : 3;";
        assertEquals(good, insn);
    }

    public int mth_returnTernary2(boolean a) {
        try {
            return a ? 6 : 3;
        } catch (Exception e) {
            return 0;
        }
    }

    @Test
    public void testReturnedTernary2() throws IOException {
        String insn = TestHelper.getAsString(getClass(), "mth_returnTernary2");
        String good = "try {\n"
                + "    return a ? 6 : 3;\n"
                + "} catch (Exception e) {\n"
                + "    return 0;\n"
                + "}";
        assertEquals(good, insn);
    }

}
