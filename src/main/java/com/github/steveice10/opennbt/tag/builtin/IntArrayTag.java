package com.github.steveice10.opennbt.tag.builtin;

import com.github.steveice10.opennbt.tag.limiter.TagLimiter;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

/**
 * A tag containing an integer array.
 */
public class IntArrayTag extends NumberArrayTag {
    public static final int ID = 11;
    private static final int[] EMPTY_ARRAY = new int[0];
    private int[] value;

    /**
     * Creates a tag.
     */
    public IntArrayTag() {
        this(EMPTY_ARRAY);
    }

    /**
     * Creates a tag.
     *
     * @param value The value of the tag.
     */
    public IntArrayTag(final int[] value) {
        if (value == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.value = value;
    }

    @Override
    public int[] getValue() {
        return this.value;
    }

    @Override
    public String asRawString() {
        return Arrays.toString(this.value);
    }

    /**
     * Sets the value of this tag.
     *
     * @param value New value of this tag.
     */
    public void setValue(final int[] value) {
        if (value == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.value = value;
    }

    /**
     * Gets a value in this tag's array.
     *
     * @param index Index of the value.
     * @return The value at the given index.
     */
    public int getValue(final int index) {
        return this.value[index];
    }

    /**
     * Sets a value in this tag's array.
     *
     * @param index Index of the value.
     * @param value Value to set.
     */
    public void setValue(final int index, final int value) {
        this.value[index] = value;
    }

    @Override
    public int length() {
        return this.value.length;
    }

    @Override
    public ListTag toListTag() {
        final ListTag list = new ListTag();
        for (final int i : this.value) {
            list.add(new IntTag(i));
        }
        return list;
    }

    @Override
    public void read(final DataInput in, final TagLimiter tagLimiter, final int nestingLevel) throws IOException {
        tagLimiter.countInt();
        this.value = new int[in.readInt()];
        tagLimiter.countBytes(4 * this.value.length);
        for (int index = 0; index < this.value.length; index++) {
            this.value[index] = in.readInt();
        }
    }

    @Override
    public void write(final DataOutput out) throws IOException {
        out.writeInt(this.value.length);
        for (final int i : this.value) {
            out.writeInt(i);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final IntArrayTag that = (IntArrayTag) o;
        return Arrays.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    @Override
    public final IntArrayTag clone() {
        return new IntArrayTag(this.value.clone());
    }

    @Override
    public int getTagId() {
        return ID;
    }
}
