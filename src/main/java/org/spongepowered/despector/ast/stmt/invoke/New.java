/*
 * The MIT License (MIT)
 *
 * Copyright (c) Despector <https://despector.voxelgenesis.com>
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
package org.spongepowered.despector.ast.stmt.invoke;

import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.despector.ast.AstVisitor;
import org.spongepowered.despector.ast.generic.TypeSignature;
import org.spongepowered.despector.ast.insn.Instruction;
import org.spongepowered.despector.ast.insn.InstructionVisitor;
import org.spongepowered.despector.util.serialization.AstSerializer;
import org.spongepowered.despector.util.serialization.MessagePacker;

import java.io.IOException;
import java.util.Arrays;

/**
 * A statement instantiating a new instance of a type.
 */
public class New implements Instruction {

    private TypeSignature type;
    private String ctor;
    private Instruction[] params;

    public New(TypeSignature type, String ctor_desc, Instruction[] args) {
        this.type = checkNotNull(type, "type");
        this.ctor = ctor_desc;
        this.params = args;
    }

    /**
     * Gets the description of the constructor being called.
     */
    public String getCtorDescription() {
        return this.ctor;
    }

    /**
     * Sets the description of the constructor being called.
     */
    public void setCtorDescription(String desc) {
        this.ctor = checkNotNull(desc, "ctor_desc");
    }

    /**
     * Gets the type description of the type being instanciated.
     */
    public TypeSignature getType() {
        return this.type;
    }

    /**
     * Sets the type description of the type being instanciated.
     */
    public void setType(TypeSignature type) {
        this.type = checkNotNull(type, "type");
    }

    /**
     * Gets the parameters of the constructor.
     */
    public Instruction[] getParameters() {
        return this.params;
    }

    /**
     * Sets the parameters of the constructor.
     */
    public void setParameters(Instruction... args) {
        this.params = checkNotNull(args, "args");
    }

    @Override
    public TypeSignature inferType() {
        return this.type;
    }

    @Override
    public void accept(AstVisitor visitor) {
        if (visitor instanceof InstructionVisitor) {
            ((InstructionVisitor) visitor).visitNew(this);
            for (Instruction insn : this.params) {
                insn.accept(visitor);
            }
        }
    }

    @Override
    public void writeTo(MessagePacker pack) throws IOException {
        pack.startMap(4);
        pack.writeString("id").writeInt(AstSerializer.STATEMENT_ID_NEW);
        pack.writeString("type");
        this.type.writeTo(pack);
        pack.writeString("ctor").writeString(this.ctor);
        pack.writeString("params").startArray(this.params.length);
        for (Instruction insn : this.params) {
            insn.writeTo(pack);
        }
        pack.endArray();
        pack.endMap();
    }

    @Override
    public String toString() {
        StringBuilder params = new StringBuilder();
        for (int i = 0; i < this.params.length; i++) {
            params.append(this.params[i]);
            if (i < this.params.length - 1) {
                params.append(", ");
            }
        }
        return "new " + this.type.getName() + "(" + params + ");";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof New)) {
            return false;
        }
        New insn = (New) obj;
        return this.type.equals(insn.type) && this.ctor.equals(insn.ctor) && Arrays.equals(this.params, insn.params);
    }

    @Override
    public int hashCode() {
        int h = 1;
        h = h * 37 + this.type.hashCode();
        h = h * 37 + this.ctor.hashCode();
        for (Instruction insn : this.params) {
            h = h * 37 + insn.hashCode();
        }
        return h;
    }

}
