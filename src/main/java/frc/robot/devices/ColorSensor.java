/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.devices;

import java.util.HashMap;
import java.util.Map;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

/**
 * Add your docs here.
 */
public class ColorSensor {

    private final I2C.Port I2C_PORT = I2C.Port.kOnboard;
    private final ColorSensorV3 COLOR_SENSOR = new ColorSensorV3(I2C_PORT);
    private final ColorMatch COLOR_MATCHER = new ColorMatch();

    private final Color BLUE_TARGET = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color GREEN_TARGET = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color RED_TARGET = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color YELLOW_TARGET = ColorMatch.makeColor(0.361, 0.524, 0.113);

    private Color targetColor;

    // Color order spinning CW: Red, Green, Blue, Yellow
    public final Map<String, String> DETECTED_2_TARGET = new HashMap<String,String>();

    public ColorSensor() {
        this.COLOR_MATCHER.addColorMatch(BLUE_TARGET);
        this.COLOR_MATCHER.addColorMatch(GREEN_TARGET);
        this.COLOR_MATCHER.addColorMatch(RED_TARGET);
        this.COLOR_MATCHER.addColorMatch(YELLOW_TARGET);

        this.DETECTED_2_TARGET.put("Yellow", "Red");
        this.DETECTED_2_TARGET.put("Green", "Blue");
        this.DETECTED_2_TARGET.put("Blue", "Yellow");
        this.DETECTED_2_TARGET.put("Red", "Green");
    }

    public Color getColor() {
        return this.COLOR_SENSOR.getColor();
    }

    public String getNamedColor() {
        ColorMatchResult match = COLOR_MATCHER.matchClosestColor(this.getColor());
        if (match.color == BLUE_TARGET) {
            return "Blue";
        } else if (match.color == RED_TARGET) {
            return "Red";
        } else if (match.color == GREEN_TARGET) {
            return "Green";
        } else if (match.color == YELLOW_TARGET) {
            return "Yellow";
        }
        return "Unknown";
    }

    public void setTargetColor(Color target) {
        this.targetColor = target;
    }

    public boolean matchColor() {
        ColorMatchResult match = COLOR_MATCHER.matchClosestColor(this.getColor());
        return match.color == this.targetColor;
    }
    
}
