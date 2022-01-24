package org.vivecraft.gui;

import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import org.vivecraft.gui.framework.TwoHandedScreen;
import org.vivecraft.provider.InputSimulator;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.widget.button.Button;

import java.util.Timer;
import java.util.TimerTask;

public class GuiKeyboard extends TwoHandedScreen
{

	private boolean isShift = false;

	private static final Timer timer = new Timer();

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void init()
	{
		String arr = minecraft.vrSettings.keyboardKeys;
		String alt = minecraft.vrSettings.keyboardKeysShift;

		this.buttons.clear();
		this.children.clear();
		//this.buttons.add(new GuiSmallButtonEx(301, this.width / 2 - 78, this.height / 6 - 14, "Hide Hud (F1): " + minecraft.gameSettings.hideGUI));

		if(this.isShift)
			arr = alt;

		int cols = 13;
		int rows = 4;
		int margin = 32;
		int spacing = 2;
		int bwidth = 25;
		double tmp = (double)arr.length() / (double)cols;
		
		if (Math.floor(tmp) == tmp)
			rows = (int) tmp;
		else
			rows = (int) (tmp+1);
		
		for (int r=0; r<rows;r++) {
			for (int i=0; i<cols;i++) {
				int c = r*cols+i;
				char x = 32;
				if (c<arr.length()) {
					x = arr.charAt(c);
				}	

				final String c1 = String.valueOf(x);

				Button butt = new Button(margin + i*(bwidth+spacing), margin + r*(20+spacing), bwidth, 20, c1,(p) -> {
						InputSimulator.typeChars(c1);
				});
				this.addButton(butt);
			}
		}		

		Minecraft instance = Minecraft.getInstance();
		this.addButton(new Button( 0, margin + 3* (20 + spacing), 30, 20, "Shift",(p) ->  {
				setShift(!GuiKeyboard.this.isShift);
		}));
		
		this.addButton(new Button(margin + 4 * (bwidth+spacing), margin + rows * (20+spacing), 5 * (bwidth+spacing), 20, " ",(p) ->  {
				InputSimulator.typeChars(" ");
		}));
		
		this.addButton(new Button(cols * (bwidth+spacing) + margin, margin , 35 , 20, "BKSP",(p) ->  {
				InputSimulator.pressKey(GLFW.GLFW_KEY_BACKSPACE);
				timer.schedule(createTask(instance, () -> InputSimulator.releaseKey(GLFW.GLFW_KEY_BACKSPACE)), 50);
		}));
		this.addButton(new Button(cols * (bwidth+spacing) + margin, margin + 2*(20 + spacing) , 35 , 20, "ENTER",(p) ->  {
				InputSimulator.pressKey(GLFW.GLFW_KEY_ENTER);
				timer.schedule(createTask(instance, () -> InputSimulator.releaseKey(GLFW.GLFW_KEY_ENTER)), 50);
		}));
		this.addButton(new Button(0, margin + (20 + spacing), 30, 20, "TAB",(p) ->  {
				InputSimulator.pressKey(GLFW.GLFW_KEY_TAB);
				timer.schedule(createTask(instance, () -> InputSimulator.releaseKey(GLFW.GLFW_KEY_TAB)), 50);
		}));
		this.addButton(new Button(0, margin, 30, 20, "ESC",(p) ->  {
				InputSimulator.pressKey(GLFW.GLFW_KEY_ESCAPE);
				timer.schedule(createTask(instance, () -> InputSimulator.releaseKey(GLFW.GLFW_KEY_ESCAPE)), 50);
		}));
		this.addButton(new Button((cols - 1) * (bwidth + spacing) + margin, margin + rows * (20 + spacing), bwidth, 20, "\u2191",(p) ->  {
				InputSimulator.pressKey(GLFW.GLFW_KEY_UP);
				timer.schedule(createTask(instance, () -> InputSimulator.releaseKey(GLFW.GLFW_KEY_UP)), 50);
		}));
		this.addButton(new Button((cols - 1) * (bwidth + spacing) + margin, margin + (rows + 1) * (20 + spacing), bwidth, 20, "\u2193",(p) ->  {
				InputSimulator.pressKey(GLFW.GLFW_KEY_DOWN);
				timer.schedule(createTask(instance, () -> InputSimulator.releaseKey(GLFW.GLFW_KEY_DOWN)), 50);
		}));
		this.addButton(new Button((cols - 2) * (bwidth + spacing) + margin, margin + (rows + 1) * (20 + spacing), bwidth, 20, "\u2190",(p) ->  {
				InputSimulator.pressKey(GLFW.GLFW_KEY_LEFT);
				timer.schedule(createTask(instance, () -> InputSimulator.releaseKey(GLFW.GLFW_KEY_LEFT)), 50);
		}));
		this.addButton(new Button(cols * (bwidth + spacing) + margin, margin + (rows + 1) * (20 + spacing), bwidth, 20, "\u2192",(p) ->  {
				InputSimulator.pressKey(GLFW.GLFW_KEY_RIGHT);
				timer.schedule(createTask(instance, () -> InputSimulator.releaseKey(GLFW.GLFW_KEY_RIGHT)), 50);
		}));
		this.addButton(new Button(margin, margin + -1 * (20 + spacing), 35, 20, "CUT",(p) ->  {
				InputSimulator.pressKey(GLFW.GLFW_KEY_LEFT_CONTROL);
				InputSimulator.pressKey(GLFW.GLFW_KEY_X);
				timer.schedule(createTask(instance, () -> {
					InputSimulator.releaseKey(GLFW.GLFW_KEY_X);
					InputSimulator.releaseKey(GLFW.GLFW_KEY_LEFT_CONTROL);
				}), 50);
		}));
		this.addButton(new Button(35 + spacing + margin, margin + -1 * (20 + spacing), 35, 20, "COPY",(p) ->  {
				InputSimulator.pressKey(GLFW.GLFW_KEY_LEFT_CONTROL);
				InputSimulator.pressKey(GLFW.GLFW_KEY_C);
				timer.schedule(createTask(instance, () -> {
					InputSimulator.releaseKey(GLFW.GLFW_KEY_C);
					InputSimulator.releaseKey(GLFW.GLFW_KEY_LEFT_CONTROL);
				}), 50);
		}));
		this.addButton(new Button(2 * (35 + spacing) + margin, margin + -1 * (20 + spacing), 35, 20, "PASTE",(p) ->  {
				InputSimulator.pressKey(GLFW.GLFW_KEY_LEFT_CONTROL);
				InputSimulator.pressKey(GLFW.GLFW_KEY_V);
				timer.schedule(createTask(instance, () -> {
					InputSimulator.releaseKey(GLFW.GLFW_KEY_V);
					InputSimulator.releaseKey(GLFW.GLFW_KEY_LEFT_CONTROL);
				}), 50);
		}));
	}

	public void setShift(boolean shift) {
		if(shift != this.isShift) {
			this.isShift = shift;
			this.reinit = true;
		}
	}
	
    /**
     * Draws the screen and all the components in it.
     */
    public void render(MatrixStack matrixstack, int mouseX, int mouseY, float partialTicks)
    {
    	this.renderBackground(matrixstack);
    	this.drawCenteredString(matrixstack, this.font, "Keyboard", this.width / 2, 2, 16777215);
    	super.render(matrixstack, 0, 0, partialTicks);

    }

	private TimerTask createTask(Minecraft instance, Runnable task) {
		return new TimerTask() {
			@Override
			public void run() {
				instance.execute(task);
			}
		};
	}

}
