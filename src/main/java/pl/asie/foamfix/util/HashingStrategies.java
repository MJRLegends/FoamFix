/**
 * This file is part of FoamFixAPI.
 *
 * FoamFixAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FoamFixAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FoamFixAPI.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Additional permission under GNU GPL version 3 section 7
 *
 * If you modify this Program, or any covered work, by linking or
 * combining it with the Minecraft game engine, the Mojang Launchwrapper,
 * the Mojang AuthLib and the Minecraft Realms library (and/or modified
 * versions of said software), containing parts covered by the terms of
 * their respective licenses, the licensors of this Program grant you
 * additional permission to convey the resulting work.
 */
package pl.asie.foamfix.util;

import com.google.common.collect.ImmutableSet;
import gnu.trove.strategy.HashingStrategy;
import gnu.trove.strategy.IdentityHashingStrategy;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.vecmath.Vector3f;
import java.util.Arrays;
import java.util.Objects;

public final class HashingStrategies {
    public static final HashingStrategy<byte[]> BYTE_ARRAY = new ByteArray();
    public static final HashingStrategy<float[]> FLOAT_ARRAY = new FloatArray();
    public static final HashingStrategy<float[][]> FLOAT_ARRAY_ARRAY = new FloatArrayArray();
    public static final HashingStrategy<int[]> INT_ARRAY = new IntArray();
    public static final HashingStrategy GENERIC = new ObjectStrategy();
    public static final HashingStrategy IDENTITY = new IdentityHashingStrategy();
    public static final HashingStrategy<ItemCameraTransforms> ITEM_CAMERA_TRANSFORMS = new ItemCameraTransformsStrategy();
    public static final HashingStrategy<ItemTransformVec3f> ITEM_TRANSFORM_VEC3F = new ItemTransformVecStrategy();

    private static final class ItemCameraTransformsStrategy implements HashingStrategy<ItemCameraTransforms> {
        @Override
        public int computeHashCode(ItemCameraTransforms object) {
            int hash = 1;
            for (ItemTransformVec3f transform : ImmutableSet.of(object.firstperson_left, object.firstperson_right,
                    object.fixed, object.ground, object.gui, object.head,
                    object.thirdperson_left, object.thirdperson_right)) {
                for (org.lwjgl.util.vector.Vector3f vector : ImmutableSet.of(transform.rotation, transform.scale, transform.translation)) {
                    hash = ((hash * 31 + Float.floatToIntBits(vector.getX())) * 31 + Float.floatToIntBits(vector.getY())) * 31 + Float.floatToIntBits(vector.getZ());
                }
            }
            return hash;
        }

        @Override
        public boolean equals(ItemCameraTransforms o1, ItemCameraTransforms o2) {
            if (o1 == null) return o1 == o2;

            return Objects.equals(o1.firstperson_left, o2.firstperson_left)
                    && Objects.equals(o1.firstperson_right, o2.firstperson_right)
                    && Objects.equals(o1.fixed, o2.fixed)
                    && Objects.equals(o1.ground, o2.ground)
                    && Objects.equals(o1.gui, o2.gui)
                    && Objects.equals(o1.head, o2.head)
                    && Objects.equals(o1.thirdperson_left, o2.thirdperson_left)
                    && Objects.equals(o1.thirdperson_right, o2.thirdperson_right);
        }
    }

    private static final class ItemTransformVecStrategy implements HashingStrategy<ItemTransformVec3f> {
        @Override
        public int computeHashCode(ItemTransformVec3f transform) {
            int hash = 1;
            for (org.lwjgl.util.vector.Vector3f vector : ImmutableSet.of(transform.rotation, transform.scale, transform.translation)) {
                hash = ((hash * 31 + Float.floatToIntBits(vector.getX())) * 31 + Float.floatToIntBits(vector.getY())) * 31 + Float.floatToIntBits(vector.getZ());
            }
            return hash;
        }

        @Override
        public boolean equals(ItemTransformVec3f o1, ItemTransformVec3f o2) {
            return Objects.equals(o1, o2);
        }
    }

    private static final class ObjectStrategy implements HashingStrategy {
        @Override
        public int computeHashCode(Object object) {
            return Objects.hashCode(object);
        }

        @Override
        public boolean equals(Object o1, Object o2) {
            return Objects.equals(o1, o2);
        }
    }

    private static final class ByteArray implements HashingStrategy<byte[]> {
        @Override
        public int computeHashCode(byte[] object) {
            return Arrays.hashCode(object);
        }

        @Override
        public boolean equals(byte[] o1, byte[] o2) {
            return Arrays.equals(o1, o2);
        }
    }

    private static final class IntArray implements HashingStrategy<int[]> {
        @Override
        public int computeHashCode(int[] object) {
            return Arrays.hashCode(object);
        }

        @Override
        public boolean equals(int[] o1, int[] o2) {
            return Arrays.equals(o1, o2);
        }
    }

    private static final class FloatArray implements HashingStrategy<float[]> {
        @Override
        public int computeHashCode(float[] object) {
            return Arrays.hashCode(object);
        }

        @Override
        public boolean equals(float[] o1, float[] o2) {
            return Arrays.equals(o1, o2);
        }
    }

    private static final class FloatArrayArray implements HashingStrategy<float[][]> {
        @Override
        public int computeHashCode(float[][] object) {
            int hash = 1;
            for (int i = 0; i < object.length; i++) {
                hash = hash * 31 + Arrays.hashCode(object[i]);
            }
            return hash;
        }

        @Override
        public boolean equals(float[][] o1, float[][] o2) {
            if (o1 == null) {
                return o1 == o2;
            } else {
                if (o1.length != o2.length)
                    return false;

                for (int i = 0; i < o1.length; i++) {
                    if (!Arrays.equals(o1[i], o2[i]))
                        return false;
                }
            }

            return true;
        }
    }
}
