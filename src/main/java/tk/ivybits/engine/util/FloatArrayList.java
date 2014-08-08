/*
 * This file is part of XyEngine.
 *
 * XyEngine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * XyEngine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with XyEngine. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package tk.ivybits.engine.util;
/**
 * @author jezek2
 */
public class FloatArrayList {
    private float[] array = new float[16];
    private int size;

    public void add(float value) {
        if (size == array.length) {
            expand();
        }

        array[size++] = value;
    }

    private void expand() {
        float[] newArray = new float[array.length << 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
    }

    public float remove(int index) {
        if (index >= size) throw new IndexOutOfBoundsException();
        float old = array[index];
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        size--;
        return old;
    }

    public float get(int index) {
        if (index >= size) throw new IndexOutOfBoundsException();
        return array[index];
    }

    public void set(int index, float value) {
        if (index >= size) throw new IndexOutOfBoundsException();
        array[index] = value;
    }

    public int size() {
        return size;
    }
}
