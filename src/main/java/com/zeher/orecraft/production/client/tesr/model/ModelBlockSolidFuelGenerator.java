package com.zeher.orecraft.production.client.tesr.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBlockSolidFuelGenerator extends ModelBase {
	
	public ModelRenderer bartop0;
	public ModelRenderer bartop1;
	public ModelRenderer bartop2;
	public ModelRenderer bartop3;
	public ModelRenderer bartop4;
	public ModelRenderer bartop5;
	public ModelRenderer barside0;
	public ModelRenderer barside1;
	public ModelRenderer barside2;
	public ModelRenderer barside3;
	public ModelRenderer barcross0;
	public ModelRenderer barcross1;
	public ModelRenderer barbottom0;
	public ModelRenderer barbottom1;
	public ModelRenderer barbottom2;
	public ModelRenderer barbottom3;
	public ModelRenderer barbottom4;
	public ModelRenderer barbottom5;
	public ModelRenderer barbottomcross0;
	public ModelRenderer barbottomcross1;
	public ModelRenderer barcrosstop0;
	public ModelRenderer barcrosstop1;
	public ModelRenderer base0;
	public ModelRenderer base1;
	public ModelRenderer base2;
	public ModelRenderer base3;
	public ModelRenderer basetop;
	public ModelRenderer side0;
	//public ModelRenderer sideconnector0;
	public ModelRenderer side1;
	//public ModelRenderer sideconnector1;
	public ModelRenderer connector0;
	public ModelRenderer connector1;
	public ModelRenderer undercircuit;
	public ModelRenderer motormount00;
	public ModelRenderer motormount01;
	public ModelRenderer motormounttop;
	public ModelRenderer motormount10;
	public ModelRenderer motormount11;
	public ModelRenderer motor0;
	public ModelRenderer motor1;
	
	public ModelBlockSolidFuelGenerator() {
		
		this.textureWidth = 256;
		this.textureHeight = 64;
		
		bartop0 = new ModelRenderer(this, 0, 60);
		bartop0.addBox(-6F, 9F, -8F, 12, 1, 1, 0F);
		bartop0.setRotationPoint(0F, 0F, 0F);
		bartop0.rotateAngleX = 0F;
		bartop0.rotateAngleY = 0F;
		bartop0.rotateAngleZ = 0F;
		bartop0.mirror = false;
		bartop1 = new ModelRenderer(this, 28, 60);
		bartop1.addBox(-6F, 9F, 7F, 12, 1, 1, 0F);
		bartop1.setRotationPoint(0F, 0F, 0F);
		bartop1.rotateAngleX = 0F;
		bartop1.rotateAngleY = 0F;
		bartop1.rotateAngleZ = 0F;
		bartop1.mirror = false;
		bartop2 = new ModelRenderer(this, 0, 56);
		bartop2.addBox(-7F, 10F, -8F, 2, 1, 1, 0F);
		bartop2.setRotationPoint(0F, 0F, 0F);
		bartop2.rotateAngleX = 0F;
		bartop2.rotateAngleY = 0F;
		bartop2.rotateAngleZ = 0F;
		bartop2.mirror = false;
		bartop3 = new ModelRenderer(this, 0, 56);
		bartop3.addBox(-7F, 10F, 7F, 2, 1, 1, 0F);
		bartop3.setRotationPoint(0F, 0F, 0F);
		bartop3.rotateAngleX = 0F;
		bartop3.rotateAngleY = 0F;
		bartop3.rotateAngleZ = 0F;
		bartop3.mirror = false;
		bartop4 = new ModelRenderer(this, 0, 56);
		bartop4.addBox(5F, 10F, -8F, 2, 1, 1, 0F);
		bartop4.setRotationPoint(0F, 0F, 0F);
		bartop4.rotateAngleX = 0F;
		bartop4.rotateAngleY = 0F;
		bartop4.rotateAngleZ = 0F;
		bartop4.mirror = false;
		bartop5 = new ModelRenderer(this, 0, 56);
		bartop5.addBox(5F, 10F, 7F, 2, 1, 1, 0F);
		bartop5.setRotationPoint(0F, 0F, 0F);
		bartop5.rotateAngleX = 0F;
		bartop5.rotateAngleY = 0F;
		bartop5.rotateAngleZ = 0F;
		bartop5.mirror = false;
		barside0 = new ModelRenderer(this, 0, 39);
		barside0.addBox(-7F, 11F, -8F, 1, 11, 1, 0F);
		barside0.setRotationPoint(0F, 0F, 0F);
		barside0.rotateAngleX = 0F;
		barside0.rotateAngleY = 0F;
		barside0.rotateAngleZ = 0F;
		barside0.mirror = false;
		barside1 = new ModelRenderer(this, 0, 39);
		barside1.addBox(-7F, 11F, 7F, 1, 11, 1, 0F);
		barside1.setRotationPoint(0F, 0F, 0F);
		barside1.rotateAngleX = 0F;
		barside1.rotateAngleY = 0F;
		barside1.rotateAngleZ = 0F;
		barside1.mirror = false;
		barside2 = new ModelRenderer(this, 0, 39);
		barside2.addBox(6F, 11F, 7F, 1, 11, 1, 0F);
		barside2.setRotationPoint(0F, 0F, 0F);
		barside2.rotateAngleX = 0F;
		barside2.rotateAngleY = 0F;
		barside2.rotateAngleZ = 0F;
		barside2.mirror = false;
		barside3 = new ModelRenderer(this, 0, 39);
		barside3.addBox(6F, 11F, -8F, 1, 11, 1, 0F);
		barside3.setRotationPoint(0F, 0F, 0F);
		barside3.rotateAngleX = 0F;
		barside3.rotateAngleY = 0F;
		barside3.rotateAngleZ = 0F;
		barside3.mirror = false;
		barcross0 = new ModelRenderer(this, 56, 56);
		barcross0.addBox(-6F, 12.5F, -7.5F, 12, 2, 1, 0F);
		barcross0.setRotationPoint(0F, 0F, 0F);
		barcross0.rotateAngleX = 0F;
		barcross0.rotateAngleY = 0F;
		barcross0.rotateAngleZ = 0F;
		barcross0.mirror = false;
		barcross1 = new ModelRenderer(this, 56, 61);
		barcross1.addBox(-6F, 12.5F, 6.5F, 12, 2, 1, 0F);
		barcross1.setRotationPoint(0F, 0F, 0F);
		barcross1.rotateAngleX = 0F;
		barcross1.rotateAngleY = 0F;
		barcross1.rotateAngleZ = 0F;
		barcross1.mirror = false;
		barbottom0 = new ModelRenderer(this, 6, 38);
		barbottom0.addBox(-7F, 23F, -7F, 1, 1, 14, 0F);
		barbottom0.setRotationPoint(0F, 0F, 0F);
		barbottom0.rotateAngleX = 0F;
		barbottom0.rotateAngleY = 0F;
		barbottom0.rotateAngleZ = 0F;
		barbottom0.mirror = false;
		barbottom1 = new ModelRenderer(this, 6, 38);
		barbottom1.addBox(6F, 23F, -7F, 1, 1, 14, 0F);
		barbottom1.setRotationPoint(0F, 0F, 0F);
		barbottom1.rotateAngleX = 0F;
		barbottom1.rotateAngleY = 0F;
		barbottom1.rotateAngleZ = 0F;
		barbottom1.mirror = false;
		barbottom2 = new ModelRenderer(this, 8, 55);
		barbottom2.addBox(-7F, 22F, -8F, 1, 1, 2, 0F);
		barbottom2.setRotationPoint(0F, 0F, 0F);
		barbottom2.rotateAngleX = 0F;
		barbottom2.rotateAngleY = 0F;
		barbottom2.rotateAngleZ = 0F;
		barbottom2.mirror = false;
		barbottom3 = new ModelRenderer(this, 8, 55);
		barbottom3.addBox(-7F, 22F, 6F, 1, 1, 2, 0F);
		barbottom3.setRotationPoint(0F, 0F, 0F);
		barbottom3.rotateAngleX = 0F;
		barbottom3.rotateAngleY = 0F;
		barbottom3.rotateAngleZ = 0F;
		barbottom3.mirror = false;
		barbottom4 = new ModelRenderer(this, 8, 55);
		barbottom4.addBox(6F, 22F, -8F, 1, 1, 2, 0F);
		barbottom4.setRotationPoint(0F, 0F, 0F);
		barbottom4.rotateAngleX = 0F;
		barbottom4.rotateAngleY = 0F;
		barbottom4.rotateAngleZ = 0F;
		barbottom4.mirror = false;
		barbottom5 = new ModelRenderer(this, 8, 55);
		barbottom5.addBox(6F, 22F, 6F, 1, 1, 2, 0F);
		barbottom5.setRotationPoint(0F, 0F, 0F);
		barbottom5.rotateAngleX = 0F;
		barbottom5.rotateAngleY = 0F;
		barbottom5.rotateAngleZ = 0F;
		barbottom5.mirror = false;
		barbottomcross0 = new ModelRenderer(this, 16, 56);
		barbottomcross0.addBox(-6F, 23F, -4F, 12, 1, 1, 0F);
		barbottomcross0.setRotationPoint(0F, 0F, 0F);
		barbottomcross0.rotateAngleX = 0F;
		barbottomcross0.rotateAngleY = 0F;
		barbottomcross0.rotateAngleZ = 0F;
		barbottomcross0.mirror = false;
		barbottomcross1 = new ModelRenderer(this, 16, 56);
		barbottomcross1.addBox(-6F, 23F, 3F, 12, 1, 1, 0F);
		barbottomcross1.setRotationPoint(0F, 0F, 0F);
		barbottomcross1.rotateAngleX = 0F;
		barbottomcross1.rotateAngleY = 0F;
		barbottomcross1.rotateAngleZ = 0F;
		barbottomcross1.mirror = false;
		barcrosstop0 = new ModelRenderer(this, 6, 38);
		barcrosstop0.addBox(-5F, 9F, -7F, 1, 1, 14, 0F);
		barcrosstop0.setRotationPoint(0F, 0F, 0F);
		barcrosstop0.rotateAngleX = 0F;
		barcrosstop0.rotateAngleY = 0F;
		barcrosstop0.rotateAngleZ = 0F;
		barcrosstop0.mirror = false;
		barcrosstop1 = new ModelRenderer(this, 6, 38);
		barcrosstop1.addBox(4F, 9F, -7F, 1, 1, 14, 0F);
		barcrosstop1.setRotationPoint(0F, 0F, 0F);
		barcrosstop1.rotateAngleX = 0F;
		barcrosstop1.rotateAngleY = 0F;
		barcrosstop1.rotateAngleZ = 0F;
		barcrosstop1.mirror = false;
		base0 = new ModelRenderer(this, 0, 0);
		base0.addBox(-6.5F, 12F, -7F, 13, 3, 14, 0F);
		base0.setRotationPoint(0F, 0F, 0F);
		base0.rotateAngleX = 0F;
		base0.rotateAngleY = 0F;
		base0.rotateAngleZ = 0F;
		base0.mirror = false;
		base1 = new ModelRenderer(this, 0, 19);
		base1.addBox(-6F, 11.5F, -6.5F, 12, 1, 13, 0F);
		base1.setRotationPoint(0F, 0F, 0F);
		base1.rotateAngleX = 0F;
		base1.rotateAngleY = 0F;
		base1.rotateAngleZ = 0F;
		base1.mirror = false;
		base2 = new ModelRenderer(this, 52, 19);
		base2.addBox(-5.5F, 11F, -6F, 11, 1, 12, 0F);
		base2.setRotationPoint(0F, 0F, 0F);
		base2.rotateAngleX = 0F;
		base2.rotateAngleY = 0F;
		base2.rotateAngleZ = 0F;
		base2.mirror = false;
		base3 = new ModelRenderer(this, 52, 34);
		base3.addBox(-4F, 10.5F, -5.5F, 8, 1, 11, 0F);
		base3.setRotationPoint(0F, 0F, 0F);
		base3.rotateAngleX = 0F;
		base3.rotateAngleY = 0F;
		base3.rotateAngleZ = 0F;
		base3.mirror = false;
		basetop = new ModelRenderer(this, 38, 37);
		basetop.addBox(-1.5F, 10F, -1.5F, 3, 1, 3, 0F);
		basetop.setRotationPoint(0F, 0F, 0F);
		basetop.rotateAngleX = 0F;
		basetop.rotateAngleY = 0F;
		basetop.rotateAngleZ = 0F;
		basetop.mirror = false;
		side0 = new ModelRenderer(this, 92, 44);
		side0.addBox(-7.5F, 12F, -7F, 1, 5, 14, 0F);
		side0.setRotationPoint(0F, 0F, 0F);
		side0.rotateAngleX = 0F;
		side0.rotateAngleY = 0F;
		side0.rotateAngleZ = 0F;
		side0.mirror = false;
		
		/**
		sideconnector0 = new ModelRenderer(this, 100, 0);
		sideconnector0.addBox(-8F, 12.5F, -3.5F, 1, 7, 7, 0F);
		sideconnector0.setRotationPoint(0F, 0F, 0F);
		sideconnector0.rotateAngleX = 0F;
		sideconnector0.rotateAngleY = 0F;
		sideconnector0.rotateAngleZ = 0F;
		sideconnector0.mirror = false;
		*/
		
		side1 = new ModelRenderer(this, 124, 44);
		side1.addBox(6.5F, 12F, -7F, 1, 5, 14, 0F);
		side1.setRotationPoint(0F, 0F, 0F);
		side1.rotateAngleX = 0F;
		side1.rotateAngleY = 0F;
		side1.rotateAngleZ = 0F;
		side1.mirror = false;
		
		/**
		sideconnector1 = new ModelRenderer(this, 100, 17);
		sideconnector1.addBox(7F, 12.5F, -3.5F, 1, 7, 7, 0F);
		sideconnector1.setRotationPoint(0F, 0F, 0F);
		sideconnector1.rotateAngleX = 0F;
		sideconnector1.rotateAngleY = 0F;
		sideconnector1.rotateAngleZ = 0F;
		sideconnector1.mirror = false;
		*/
		
		connector0 = new ModelRenderer(this, 74, 0);
		connector0.addBox(-3.5F, 12.5F, -8F, 7, 7, 1, 0F);
		connector0.setRotationPoint(0F, 0F, 0F);
		connector0.rotateAngleX = 0F;
		connector0.rotateAngleY = 0F;
		connector0.rotateAngleZ = 0F;
		connector0.mirror = false;
		connector1 = new ModelRenderer(this, 74, 10);
		connector1.addBox(-3.5F, 12.5F, 7F, 7, 7, 1, 0F);
		connector1.setRotationPoint(0F, 0F, 0F);
		connector1.rotateAngleX = 0F;
		connector1.rotateAngleY = 0F;
		connector1.rotateAngleZ = 0F;
		connector1.mirror = false;
		undercircuit = new ModelRenderer(this, 119, 0);
		undercircuit.addBox(-6F, 15F, -6F, 4, 6, 12, 0F);
		undercircuit.setRotationPoint(0F, 0F, 0F);
		undercircuit.rotateAngleX = 0F;
		undercircuit.rotateAngleY = 0F;
		undercircuit.rotateAngleZ = 0F;
		undercircuit.mirror = false;
		motormount00 = new ModelRenderer(this, 118, 20);
		motormount00.addBox(0F, 17F, -6F, 6, 4, 1, 0F);
		motormount00.setRotationPoint(0F, 0F, 0F);
		motormount00.rotateAngleX = 0F;
		motormount00.rotateAngleY = 0F;
		motormount00.rotateAngleZ = 0F;
		motormount00.mirror = false;
		motormount01 = new ModelRenderer(this, 118, 28);
		motormount01.addBox(1F, 16F, -6F, 4, 6, 1, 0F);
		motormount01.setRotationPoint(0F, 0F, 0F);
		motormount01.rotateAngleX = 0F;
		motormount01.rotateAngleY = 0F;
		motormount01.rotateAngleZ = 0F;
		motormount01.mirror = false;
		motormounttop = new ModelRenderer(this, 154, 0);
		motormounttop.addBox(1F, 15F, -6F, 4, 1, 12, 0F);
		motormounttop.setRotationPoint(0F, 0F, 0F);
		motormounttop.rotateAngleX = 0F;
		motormounttop.rotateAngleY = 0F;
		motormounttop.rotateAngleZ = 0F;
		motormounttop.mirror = false;
		motormount10 = new ModelRenderer(this, 134, 20);
		motormount10.addBox(0F, 17F, 5F, 6, 4, 1, 0F);
		motormount10.setRotationPoint(0F, 0F, 0F);
		motormount10.rotateAngleX = 0F;
		motormount10.rotateAngleY = 0F;
		motormount10.rotateAngleZ = 0F;
		motormount10.mirror = false;
		motormount11 = new ModelRenderer(this, 134, 28);
		motormount11.addBox(1F, 16F, 5F, 4, 6, 1, 0F);
		motormount11.setRotationPoint(0F, 0F, 0F);
		motormount11.rotateAngleX = 0F;
		motormount11.rotateAngleY = 0F;
		motormount11.rotateAngleZ = 0F;
		motormount11.mirror = false;
		motor0 = new ModelRenderer(this, 154, 15);
		motor0.addBox(1F, 18F, -5F, 4, 2, 10, 0F);
		motor0.setRotationPoint(-3F, -19F, 5F);
		motor0.rotateAngleX = 0F;
		motor0.rotateAngleY = 0F;
		motor0.rotateAngleZ = 0F;
		motor0.mirror = false;
		motor1 = new ModelRenderer(this, 154, 29);
		motor1.addBox(2F, 17F, -5F, 2, 4, 10, 0F);
		motor1.setRotationPoint(-3F, -19F, 5F);
		motor1.rotateAngleX = 0F;
		motor1.rotateAngleY = 0F;
		motor1.rotateAngleZ = 0F;
		motor1.mirror = false;
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		bartop0.render(f5);
		bartop1.render(f5);
		bartop2.render(f5);
		bartop3.render(f5);
		bartop4.render(f5);
		bartop5.render(f5);
		barside0.render(f5);
		barside1.render(f5);
		barside2.render(f5);
		barside3.render(f5);
		barcross0.render(f5);
		barcross1.render(f5);
		barbottom0.render(f5);
		barbottom1.render(f5);
		barbottom2.render(f5);
		barbottom3.render(f5);
		barbottom4.render(f5);
		barbottom5.render(f5);
		barbottomcross0.render(f5);
		barbottomcross1.render(f5);
		barcrosstop0.render(f5);
		barcrosstop1.render(f5);
		base0.render(f5);
		base1.render(f5);
		base2.render(f5);
		base3.render(f5);
		basetop.render(f5);
		side0.render(f5);
		//sideconnector0.render(f5);
		side1.render(f5);
		//sideconnector1.render(f5);
		connector0.render(f5);
		connector1.render(f5);
		undercircuit.render(f5);
		motormount00.render(f5);
		motormount01.render(f5);
		motormounttop.render(f5);
		motormount10.render(f5);
		motormount11.render(f5);
		motormount01.render(f5);
		motormount00.render(f5);
	}
	
	public void renderBase(float f5) {
		bartop0.render(f5);
		bartop1.render(f5);
		bartop2.render(f5);
		bartop3.render(f5);
		bartop4.render(f5);
		bartop5.render(f5);
		barside0.render(f5);
		barside1.render(f5);
		barside2.render(f5);
		barside3.render(f5);
		barcross0.render(f5);
		barcross1.render(f5);
		barbottom0.render(f5);
		barbottom1.render(f5);
		barbottom2.render(f5);
		barbottom3.render(f5);
		barbottom4.render(f5);
		barbottom5.render(f5);
		barbottomcross0.render(f5);
		barbottomcross1.render(f5);
		barcrosstop0.render(f5);
		barcrosstop1.render(f5);
		base0.render(f5);
		base1.render(f5);
		base2.render(f5);
		base3.render(f5);
		basetop.render(f5);
		side0.render(f5);
		//sideconnector0.render(f5);
		side1.render(f5);
		//sideconnector1.render(f5);
		connector0.render(f5);
		connector1.render(f5);
		undercircuit.render(f5);
		motormount00.render(f5);
		motormount01.render(f5);
		motormounttop.render(f5);
		motormount10.render(f5);
		motormount11.render(f5);
		motormount01.render(f5);
		motormount00.render(f5);
	}
	
	public void renderMotor(float f5) {
		motor0.render(f5);
		motor1.render(f5);
	}
	
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
	
}
