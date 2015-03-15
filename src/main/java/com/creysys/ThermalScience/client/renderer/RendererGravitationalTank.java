package com.creysys.ThermalScience.client.renderer;

import com.creysys.ThermalScience.tileEntity.TileEntityGravitationalTank;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;
import static org.lwjgl.util.glu.GLU.GLU_POINT;

/**
 * Created by Creysys on 14 Mar 15.
 */
public class RendererGravitationalTank extends TileEntitySpecialRenderer {

    Sphere sphere;

    public RendererGravitationalTank(){
        sphere = new Sphere();
        sphere.setTextureFlag(false);
        sphere.setOrientation(GLU.GLU_OUTSIDE);
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f1) {

        if(!(tileEntity instanceof TileEntityGravitationalTank)){
            return;
        }

        TileEntityGravitationalTank gravitationalTank = (TileEntityGravitationalTank)tileEntity;


        if(gravitationalTank.fluid != null){
            bindTexture(TextureMap.locationBlocksTexture);
            IIcon icon = gravitationalTank.fluid.getFluid().getStillIcon();

            float radius = (tileEntity.getWorldObj().getTotalWorldTime() / 100f) % 1;

            GL11.glPushMatrix();

            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glTranslated(x + 0.5f, y + 0.5f, z + 0.5f);
            renderSphere(radius, icon.getMinU(), icon.getMinV(), icon.getMaxU(), icon.getMaxV());

            GL11.glPopMatrix();
        }


    }

    public void renderSphere(float radius, float minU, float minV, float maxU, float maxV) {
        int slices = 10;
        int stacks = 10;
        float textureQuality = 4;


        float rho, drho, theta, dtheta;
        float x, y, z;
        float s, t, ds, dt;
        int i, j, imin, imax;
        boolean normals = true;
        float nsign = 1f;

        drho = (float) Math.PI / stacks;
        dtheta = 2.0f * (float) Math.PI / slices;

        glBegin(GL_TRIANGLE_FAN);
        glNormal3f(0.0f, 0.0f, 1.0f);
        glTexCoord2f(minU, maxV);
        glVertex3f(0.0f, 0.0f, nsign * radius);
        for (j = 0; j <= slices; j++) {
            theta = (j == slices) ? 0.0f : j * dtheta;
            x = -(float)Math.sin(theta) * (float)Math.sin(drho);
            y = (float)Math.cos(theta) * (float)Math.sin(drho);
            z = nsign * (float)Math.cos(drho);
            if (normals) {
                glNormal3f(x * nsign, y * nsign, z * nsign);
            }
            glTexCoord2f(minU + (maxU - minU) * j / (slices + 1), minV + (maxV - minV) * j / (slices + 1));
            glVertex3f(x * radius, y * radius, z * radius);
        }
        glEnd();

        ds = 1.0f / slices;
        dt = 1.0f / stacks;
        t = 1.0f;
        imin = 1;
        imax = stacks - 1;
        for (i = imin; i < imax; i++) {
            rho = i * drho;
            glBegin(GL_QUAD_STRIP);
            s = 0.0f;
            for (j = 0; j <= slices; j++) {
                theta = (j == slices) ? 0.0f : j * dtheta;
                x = -(float)Math.sin(theta) * (float)Math.sin(rho);
                y = (float)Math.cos(theta) * (float)Math.sin(rho);
                z = nsign * (float)Math.cos(rho);
                if (normals) {
                    glNormal3f(x * nsign, y * nsign, z * nsign);
                }


                glTexCoord2f(minU + ((maxU - minU) * ((s * textureQuality) % 1)), minV + ((maxV - minV) * ((t * textureQuality) % 1)));



                glVertex3f(x * radius, y * radius, z * radius);
                x = -(float)Math.sin(theta) * (float)Math.sin(rho + drho);
                y = (float)Math.cos(theta) * (float)Math.sin(rho + drho);
                z = nsign * (float)Math.cos(rho + drho);
                if (normals) {
                    glNormal3f(x * nsign, y * nsign, z * nsign);
                }


                glTexCoord2f(minU + ((maxU - minU) * ((s * textureQuality) % 1)), minV + ((maxV - minV) * (((t - dt) * textureQuality) % 1)));



                s += ds;
                glVertex3f(x * radius, y * radius, z * radius);
            }
            glEnd();
            t -= dt;
        }
        glBegin(GL_TRIANGLE_FAN);
        glNormal3f(0.0f, 0.0f, -1.0f);
        glVertex3f(0.0f, 0.0f, -radius * nsign);
        rho = (float)Math.PI - drho;
        s = 1.0f;
        for (j = slices; j >= 0; j--) {
            theta = (j == slices) ? 0.0f : j * dtheta;
            x = -(float)Math.sin(theta) * (float)Math.sin(rho);
            y = (float)Math.cos(theta) * (float)Math.sin(rho);
            z = nsign * (float)Math.cos(rho);
            if (normals)
                glNormal3f(x * nsign, y * nsign, z * nsign);
            s -= ds;
            glTexCoord2f(minU + (maxU - minU) * (slices - j) / (slices + 1), minV + (maxV - minV) * (slices - j) / (slices + 1));
            glVertex3f(x * radius, y * radius, z * radius);
        }
        glEnd();
    }
}
