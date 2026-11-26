package aoc.kingdoms.lukasz.menu;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.map.map.MapModeManager;
import aoc.kingdoms.lukasz.menusInGame.Right.InGame_Right;
import com.badlogic.gdx.graphics.Color;

public class Colors {
   public static Color COLOR_TITLE = new Color(0.70980394F, 0.17254902F, 0.1254902F, 1.0F);
   public static Color COLOR_GRADIENT_TITLE_BLUE = new Color(0.105882354F, 0.16078432F, 0.2901961F, 0.775F);
   public static Color COLOR_BOX_HOVER = new Color(0.3529412F, 0.27450982F, 0.33333334F, 1.0F);
   public static Color COLOR_BOX_ACTIVE = new Color(0.5686275F, 0.47058824F, 0.5294118F, 1.0F);
   public static Color COLOR_STATS_RECT_BG = new Color(0.078431375F, 0.039215688F, 0.09803922F, 1.0F);
   public static Color COLOR_STATS_RECT_BG_RED = new Color(0.13725491F, 0.078431375F, 0.09803922F, 1.0F);
   public static Color COLOR_STATS_RECT_BG_RED_OVER = new Color(0.09803922F, 0.05882353F, 0.078431375F, 1.0F);
   public static Color COLOR_GRADIENT_OVER_BG_RED = new Color(0.14117648F, 0.07450981F, 0.09411765F, 1.0F);
   public static Color COLOR_GRADIENT_OVER_BG_RED_OVER = new Color(0.21960784F, 0.09411765F, 0.10980392F, 1.0F);
   public static Color COLOR_GRAY = new Color(0.13725491F, 0.14117648F, 0.16470589F, 1.0F);
   public static Color COLOR_GRAY2 = new Color(0.09803922F, 0.11764706F, 0.18039216F, 1.0F);
   public static Color COLOR_GRADIENT_OVER_BLUE = new Color(0.078431375F, 0.13725491F, 0.1764706F, 0.75F);
   public static Color COLOR_GRADIENT_OVER_BLUE2 = new Color(0.039215688F, 0.09803922F, 0.13725491F, 0.75F);
   public static Color COLOR_GRADIENT_OVER_BLUE3 = new Color(0.11764706F, 0.19607843F, 0.29411766F, 0.75F);
   public static Color COLOR_GRADIENT_BG_BLUE = new Color(0.039215688F, 0.05882353F, 0.11764706F, 0.75F);
   public static Color COLOR_GRADIENT_OVER_GREEN = new Color(0.13725491F, 0.21568628F, 0.15686275F, 0.75F);
   public static Color COLOR_GRADIENT_BG_GREEN = new Color(0.078431375F, 0.11764706F, 0.09803922F, 0.75F);
   public static Color COLOR_GRADIENT_OVER_INFO = new Color(0.078431375F, 0.13725491F, 0.1764706F, 0.75F);
   public static Color COLOR_GRADIENT_BG_INFO = new Color(0.039215688F, 0.05882353F, 0.11764706F, 0.75F);
   public static Color COLOR_GRADIENT_OVER = new Color(0.16862746F, 0.08627451F, 0.10980392F, 0.75F);
   public static Color COLOR_GRADIENT_BG = new Color(0.09019608F, 0.047058824F, 0.05882353F, 0.75F);
   public static Color COLOR_UNKNOWN = new Color(0.0F, 0.0F, 0.0F, 1.0F);
   public static Color COLOR_BOX_FRAME = new Color(0.50980395F, 0.42352942F, 0.23529412F, 1.0F);
   public static Color COLOR_BOX_FRAME2 = new Color(0.77254903F, 0.6117647F, 0.2627451F, 1.0F);
   public static Color COLOR_BOX_GOLD = new Color(0.5686275F, 0.49019608F, 0.2901961F, 1.0F);
   public static Color COLOR_LOGO = new Color(0.69803923F, 0.54901963F, 0.23529412F, 1.0F);
   public static Color COLOR_TEXT_TITLE = new Color(0.9607843F, 0.9607843F, 0.88235295F, 1.0F);
   public static Color COLOR_TEXT_DESC = new Color(0.7647059F, 0.7647059F, 0.7254902F, 1.0F);
   public static Color COLOR_TEXT_DESC_RIGHT = new Color(0.64705884F, 0.64705884F, 0.60784316F, 1.0F);
   public static Color COLOR_TEXT_GOLD = new Color(0.7647059F, 0.5686275F, 0.1764706F, 1.0F);
   public static Color COLOR_TEXT_RESEARCH = new Color(0.4F, 0.6F, 0.8F, 1.0F);
   public static Color COLOR_TEXT_POSITIVE = new Color(0.27450982F, 0.50980395F, 0.23529412F, 1.0F);
   public static Color COLOR_TEXT_NEGATIVE = new Color(0.9607843F, 0.29411766F, 0.29411766F, 1.0F);
   public static Color BUTTON_BUILDING_COLORS_STATS = new Color(0.039215688F, 0.05882353F, 0.09803922F, 0.6F);
   public static Color COLOR_NOTIFICATION_BG = new Color(0.039215688F, 0.05882353F, 0.11764706F, 0.75F);
   public static Color COLOR_NOTIFICATION_OVER = new Color(0.13725491F, 0.15686275F, 0.21568628F, 0.75F);
   public static Color COLOR_NOTIFICATION_BG_GREEN = new Color(0.078431375F, 0.11764706F, 0.09803922F, 0.75F);
   public static Color COLOR_NOTIFICATION_OVER_GREEN = new Color(0.13725491F, 0.21568628F, 0.15686275F, 0.75F);
   public static Color COLOR_NOTIFICATION_BG_RED = new Color(0.14117648F, 0.07450981F, 0.09411765F, 1.0F);
   public static Color COLOR_NOTIFICATION_OVER_RED = new Color(0.27450982F, 0.10980392F, 0.10980392F, 0.75F);
   public static Color HOVER_TITLE = new Color(0.9764706F, 0.99215686F, 1.0F, 1.0F);
   public static Color HOVER_TITLE_2 = new Color(0.972549F, 0.92941177F, 0.8980392F, 1.0F);
   public static Color HOVER_IMPORTANT = new Color(0.98039216F, 0.98039216F, 0.84313726F, 1.0F);
   public static Color HOVER_LEFT = new Color(0.92156863F, 0.92156863F, 0.88235295F, 1.0F);
   public static Color HOVER_LEFT2 = new Color(0.7647059F, 0.7647059F, 0.7647059F, 1.0F);
   public static Color HOVER_RIGHT = new Color(0.6862745F, 0.6862745F, 0.6862745F, 1.0F);
   public static Color HOVER_RIGHT2 = new Color(0.5294118F, 0.5294118F, 0.5294118F, 1.0F);
   public static Color HOVER_RIGHT3 = new Color(0.33333334F, 0.33333334F, 0.33333334F, 1.0F);
   public static Color HOVER_GOLD = new Color(0.8039216F, 0.6666667F, 0.27450982F, 1.0F);
   public static Color HOVER_YELLOW = new Color(0.8627451F, 0.9019608F, 0.54901963F, 1.0F);
   public static Color HOVER_POSITIVE = new Color(0.27450982F, 0.50980395F, 0.23529412F, 1.0F);
   public static Color HOVER_NEGATIVE = new Color(0.70980394F, 0.17254902F, 0.1254902F, 1.0F);
   public static Color HOVER_NEUTRAL = new Color(0.8235294F, 0.8235294F, 0.78431374F, 1.0F);
   public static Color HOVER_ATTACK = new Color(0.70980394F, 0.17254902F, 0.1254902F, 1.0F);
   public static Color HOVER_DEFENSE = new Color(0.05882353F, 0.49019608F, 0.6666667F, 1.0F);
   public static Color HOVER_LINE1 = new Color(0.06666667F, 0.07450981F, 0.09019608F, 1.0F);
   public static Color HOVER_LINE2 = new Color(0.16862746F, 0.1764706F, 0.1882353F, 1.0F);
   public static Color COLOR_TOP_STATS2 = new Color(0.92156863F, 0.9411765F, 0.8627451F, 1.0F);
   public static Color COLOR_TOP_STATS3 = new Color(0.8627451F, 0.84313726F, 0.74509805F, 1.0F);
   public static Color COLOR_TOP_STATS4 = new Color(0.9019608F, 0.8627451F, 0.7254902F, 1.0F);
   public static Color COLOR_TOP_STATS5 = new Color(0.78431374F, 0.7647059F, 0.6666667F, 1.0F);
   public static Color COLOR_TOP_STATS = new Color(0.98039216F, 0.99607843F, 0.9411765F, 1.0F);
   public static Color COLOR_TOP_STATS_HOVER = new Color(0.84313726F, 0.84313726F, 0.7647059F, 1.0F);
   public static Color COLOR_TOP_STATS_ACTIVE = new Color(0.7254902F, 0.7254902F, 0.627451F, 1.0F);
   public static Color COLOR_TOP_STATS_RIGHT = new Color(0.627451F, 0.50980395F, 0.29411766F, 1.0F);
   public static Color COLOR_TOP_STATS_RIGHT_HOVER = new Color(0.84313726F, 0.84313726F, 0.7647059F, 1.0F);
   public static Color COLOR_TOP_STATS_RIGHT_ACTIVE = new Color(0.7254902F, 0.7254902F, 0.627451F, 1.0F);
   public static Color COLOR_TEXT_MODIFIER_POSITIVE = new Color(0.27450982F, 0.50980395F, 0.23529412F, 1.0F);
   public static Color COLOR_TEXT_MODIFIER_POSITIVE_HOVER = new Color(0.27450982F, 0.47058824F, 0.19607843F, 1.0F);
   public static Color COLOR_TEXT_MODIFIER_POSITIVE_ACTIVE = new Color(0.23529412F, 0.43137255F, 0.19607843F, 1.0F);
   public static Color COLOR_TEXT_MODIFIER_NEUTRAL = new Color(0.8235294F, 0.8235294F, 0.78431374F, 1.0F);
   public static Color COLOR_TEXT_MODIFIER_NEUTRAL_HOVER = new Color(0.8627451F, 0.8627451F, 0.78431374F, 1.0F);
   public static Color COLOR_TEXT_MODIFIER_NEUTRAL_ACTIVE = new Color(0.9019608F, 0.9019608F, 0.8627451F, 1.0F);
   public static Color COLOR_TEXT_MODIFIER_NEGATIVE = new Color(0.70980394F, 0.17254902F, 0.1254902F, 1.0F);
   public static Color COLOR_TEXT_MODIFIER_NEGATIVE_HOVER = new Color(0.70980394F, 0.17254902F, 0.1254902F, 1.0F);
   public static Color COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE = new Color(0.6509804F, 0.14117648F, 0.09411765F, 1.0F);
   public static Color COLOR_INFO_BOX = new Color(0.13725491F, 0.15686275F, 0.1764706F, 1.0F);
   public static Color COLOR_INFO_BOX2 = new Color(0.8509804F, 0.7607843F, 0.59607846F, 1.0F);
   public static Color COLOR_INFO_BOX2_BOT = new Color(0.47058824F, 0.4117647F, 0.37254903F, 1.0F);
   public static Color COLOR_INGAME_GOLD = new Color(0.87058824F, 0.85882354F, 0.12941177F, 1.0F);
   public static Color COLOR_INGAME_GOLD_HOVER = new Color(0.75686276F, 0.75686276F, 0.0F, 1.0F);
   public static Color COLOR_INGAME_GOLD_ACTIVE = new Color(0.6901961F, 0.6901961F, 0.0F, 1.0F);
   public static Color COLOR_POPULATION = new Color(0.392F, 0.533F, 0.251F, 1.0F);
   public static Color COLOR_POPULATION_HOVER = new Color(0.595F, 0.743F, 0.427F, 1.0F);
   public static Color COLOR_POPULATION_ACTIVE = new Color(0.4F, 0.51F, 0.3F, 1.0F);
   public static Color COLOR_TEXT_ECONOMY = new Color(0.776F, 0.518F, 0.227F, 1.0F);
   public static Color COLOR_TEXT_ECONOMY_HOVER = new Color(0.708F, 0.448F, 0.173F, 1.0F);
   public static Color COLOR_TEXT_ECONOMY_ACTIVE = new Color(0.552F, 0.36F, 0.141F, 1.0F);
   public static Color COLOR_GRADIENT = new Color(0.09803922F, 0.15686275F, 0.23529412F, 1.0F);
   public static Color COLOR_GRADIENT_HOVER = new Color(0.19607843F, 0.13725491F, 0.11764706F, 1.0F);
   public static Color TEXT_TOP_BOT = new Color(0.3529412F, 0.3529412F, 0.3529412F, 1.0F);
   public static Color TEXT_CURRENT_SITUATION = new Color(0.1254902F, 0.1254902F, 0.1254902F, 1.0F);
   public static Color TEXT_CURRENT_SITUATION_HOVER = new Color(0.1764706F, 0.1764706F, 0.1764706F, 1.0F);
   public static Color TEXT_CURRENT_SITUATION_ACTIVE = new Color(0.23529412F, 0.23529412F, 0.23529412F, 1.0F);
   public static Color BUTTON_TEXT = new Color(0.58F, 0.55F, 0.55F, 1.0F);
   public static Color BUTTON_TEXT_BRIGHT = new Color(0.675F, 0.65F, 0.65F, 1.0F);
   public static Color BUTTON_TEXT_BRIGHT2 = new Color(0.8F, 0.75F, 0.75F, 1.0F);
   public static Color BUTTON_TEXT_ACTIVE = new Color(0.98F, 0.98F, 0.843F, 1.0F);
   public static Color BUTTON_TEXT_HOVERED = new Color(0.705F, 0.705F, 0.647F, 1.0F);
   public static Color BUTTON_TEXT_DISABLED = new Color(0.49F, 0.49F, 0.49F, 0.5F);
   public static Color BUTTON_TEXT_DESC_SIMPLE = new Color(0.5686275F, 0.54901963F, 0.5294118F, 1.0F);
   public static Color BUTTON_TEXT2 = new Color(0.9F, 0.9F, 0.865F, 1.0F);
   public static Color BUTTON_TEXT_ACTIVE2 = new Color(0.92F, 0.92F, 0.85F, 1.0F);
   public static Color BUTTON_TEXT_HOVERED2 = new Color(0.75F, 0.75F, 0.7F, 1.0F);
   public static Color COLOR_TEXT_PIE_CHART_STATS = new Color(0.8F, 0.8F, 0.8F, 1.0F);
   public static Color COLOR_SLIDER_LEFT_BG = new Color(0.11764706F, 0.13725491F, 0.23529412F, 1.0F);
   public static Color COLOR_SLIDER_RIGHT_BG = new Color(0.98039216F, 0.98039216F, 0.98039216F, 1.0F);
   public static Color COLOR_COLOR_PICKER_RGB_BG = new Color(0.047058824F, 0.0627451F, 0.078431375F, 0.55F);
   public static Color COLOR_LOADING_SPLIT_ACTIVE = new Color(0.96862745F, 0.76862746F, 0.41960785F, 0.65F);
   public static Color COLOR_LOADING_SPLIT = new Color(0.77254903F, 0.6117647F, 0.2627451F, 0.35F);
   public static Color TECH_RESEARCHED = new Color(0.50980395F, 0.39215687F, 0.13725491F, 1.0F);
   public static Color TECH_RESEARCHED2 = new Color(0.78431374F, 0.627451F, 0.2627451F, 1.0F);
   public static Color TECH_RESEARCHED3 = new Color(0.6627451F, 0.5137255F, 0.1882353F, 1.0F);
   public static Color TECH_BLUE = new Color(0.09411765F, 0.16862746F, 0.23529412F, 1.0F);
   public static Color TECH_BLUE2 = new Color(0.078431375F, 0.3137255F, 0.49019608F, 1.0F);
   public static Color TECH_BLUE3 = new Color(0.105882354F, 0.22352941F, 0.30980393F, 1.0F);
   public static Color TECH_GRAY = new Color(0.13725491F, 0.13725491F, 0.13725491F, 1.0F);
   public static Color TECH_GRAY2 = new Color(0.28235295F, 0.28235295F, 0.28235295F, 1.0F);
   public static Color TECH_GRAY3 = new Color(0.21568628F, 0.21568628F, 0.21568628F, 1.0F);
   public static float PROVINCE_ALPHA_POPULATION = 0.5F;
   public static Color[] COLOR_POPULATION_VIEW = new Color[]{
      new Color(0.8627451F, 0.93333334F, 0.78039217F, PROVINCE_ALPHA_POPULATION),
      new Color(0.8F, 0.92941177F, 0.7372549F, PROVINCE_ALPHA_POPULATION),
      new Color(0.6901961F, 0.89411765F, 0.59607846F, PROVINCE_ALPHA_POPULATION),
      new Color(0.6117647F, 0.8666667F, 0.49019608F, PROVINCE_ALPHA_POPULATION),
      new Color(0.5647059F, 0.87058824F, 0.3137255F, PROVINCE_ALPHA_POPULATION),
      new Color(0.41568628F, 0.7921569F, 0.23529412F, PROVINCE_ALPHA_POPULATION),
      new Color(0.37254903F, 0.7294118F, 0.19607843F, PROVINCE_ALPHA_POPULATION),
      new Color(0.30588236F, 0.6039216F, 0.16078432F, PROVINCE_ALPHA_POPULATION),
      new Color(0.2509804F, 0.49019608F, 0.13333334F, PROVINCE_ALPHA_POPULATION),
      new Color(0.20392157F, 0.4F, 0.10980392F, PROVINCE_ALPHA_POPULATION),
      new Color(0.14509805F, 0.28627452F, 0.078431375F, PROVINCE_ALPHA_POPULATION)
   };
   public static Color[] COLOR_ECONOMY = new Color[]{
      new Color(1.0F, 0.92156863F, 0.8F, PROVINCE_ALPHA_POPULATION),
      new Color(1.0F, 0.83137256F, 0.65882355F, PROVINCE_ALPHA_POPULATION),
      new Color(1.0F, 0.77254903F, 0.56078434F, PROVINCE_ALPHA_POPULATION),
      new Color(1.0F, 0.7294118F, 0.47843137F, PROVINCE_ALPHA_POPULATION),
      new Color(1.0F, 0.63529414F, 0.3254902F, PROVINCE_ALPHA_POPULATION),
      new Color(0.96862745F, 0.54509807F, 0.19215687F, PROVINCE_ALPHA_POPULATION),
      new Color(0.9411765F, 0.4627451F, 0.019607844F, PROVINCE_ALPHA_POPULATION),
      new Color(0.88235295F, 0.3882353F, 0.0627451F, PROVINCE_ALPHA_POPULATION),
      new Color(0.7921569F, 0.24313726F, 0.02745098F, PROVINCE_ALPHA_POPULATION),
      new Color(0.7137255F, 0.09803922F, 0.015686275F, PROVINCE_ALPHA_POPULATION),
      new Color(0.654902F, 0.08627451F, 0.011764706F, PROVINCE_ALPHA_POPULATION)
   };
   public static Color[] COLOR_PROVINCE_INCOME = new Color[]{
      new Color(0.9882353F, 0.93333334F, 0.49411765F, PROVINCE_ALPHA_POPULATION), new Color(0.77254903F, 0.5176471F, 0.14509805F, PROVINCE_ALPHA_POPULATION)
   };
   public static Color[] COLOR_PROVINCE_INFRASTRUCTURE = new Color[]{
      new Color(0.8509804F, 0.7372549F, 0.5686275F, PROVINCE_ALPHA_POPULATION), new Color(0.42745098F, 0.40392157F, 0.14117648F, PROVINCE_ALPHA_POPULATION)
   };
   public static Color[] COLOR_PROVINCE_PROVINCE_VALUE = new Color[]{
      new Color(0.8509804F, 0.7372549F, 0.5686275F, PROVINCE_ALPHA_POPULATION), new Color(0.23137255F, 0.3254902F, 0.19607843F, PROVINCE_ALPHA_POPULATION)
   };
   public static Color[] COLOR_PROVINCE_RED = new Color[]{
      new Color(0.81960785F, 0.7137255F, 0.6862745F, PROVINCE_ALPHA_POPULATION), new Color(0.7058824F, 0.1764706F, 0.11764706F, PROVINCE_ALPHA_POPULATION)
   };
   public static Color[] COLOR_PROVINCE_MANPOWER = new Color[]{
      new Color(0.7058824F, 0.74509805F, 0.78431374F, PROVINCE_ALPHA_POPULATION), new Color(0.11764706F, 0.15686275F, 0.27450982F, PROVINCE_ALPHA_POPULATION)
   };
   public static Color[] COLOR_PROVINCE_DEVASTATION = new Color[]{
      new Color(1.0F, 0.9019608F, 0.4509804F, PROVINCE_ALPHA_POPULATION), new Color(0.9411765F, 0.23529412F, 0.039215688F, PROVINCE_ALPHA_POPULATION)
   };
   public static Color[] COLOR_PROVINCE_TAX = new Color[]{
      new Color(0.91764706F, 0.8F, 0.3764706F, PROVINCE_ALPHA_POPULATION), new Color(0.6862745F, 0.5254902F, 0.19215687F, PROVINCE_ALPHA_POPULATION)
   };
   public static Color[] COLOR_PROVINCE_ECONOMY = new Color[]{
      new Color(1.0F, 0.83137256F, 0.65882355F, PROVINCE_ALPHA_POPULATION), new Color(0.7921569F, 0.24313726F, 0.02745098F, PROVINCE_ALPHA_POPULATION)
   };
   public static Color COLOR_PROVINCE_DEFENSE = new Color(0.28235295F, 0.36078432F, 0.36078432F, PROVINCE_ALPHA_POPULATION);
   public static Color[] COLOR_PROVINCE_FORT = new Color[]{
      new Color(0.8235294F, 0.8235294F, 0.78431374F, PROVINCE_ALPHA_POPULATION), new Color(0.28235295F, 0.36078432F, 0.36078432F, PROVINCE_ALPHA_POPULATION)
   };
   public static Color[] COLOR_PROVINCE_LOOT = new Color[]{
      new Color(0.972549F, 0.96862745F, 0.7647059F, PROVINCE_ALPHA_POPULATION), new Color(0.92156863F, 0.41960785F, 0.08235294F, PROVINCE_ALPHA_POPULATION)
   };

   public static Color getColorButtonHover(boolean isActive, boolean isHovered) {
      if (isActive) {
         return BUTTON_TEXT_ACTIVE;
      } else {
         return isHovered ? BUTTON_TEXT_HOVERED : BUTTON_TEXT_BRIGHT;
      }
   }

   public static Color getColorButton(boolean isActive, boolean isHovered) {
      if (isActive) {
         return BUTTON_TEXT_ACTIVE;
      } else {
         return isHovered ? BUTTON_TEXT_HOVERED : BUTTON_TEXT;
      }
   }

   public static Color getColorButtonHover2(boolean isActive, boolean isHovered) {
      if (isActive) {
         return BUTTON_TEXT_ACTIVE;
      } else {
         return isHovered ? BUTTON_TEXT_HOVERED : BUTTON_TEXT_BRIGHT2;
      }
   }

   public static Color getColorTopStats(boolean isActive, boolean isHovered) {
      if (isActive) {
         return COLOR_TOP_STATS_ACTIVE;
      } else {
         return isHovered ? COLOR_TOP_STATS_HOVER : COLOR_TOP_STATS;
      }
   }

   public static Color getColorTopStats_Outliner(boolean isActive, boolean isHovered) {
      if (isActive) {
         return COLOR_TOP_STATS_ACTIVE;
      } else if (isHovered) {
         return COLOR_TOP_STATS_HOVER;
      } else {
         return InGame_Right.outlinerInView ? COLOR_TOP_STATS : new Color(COLOR_TOP_STATS.r, COLOR_TOP_STATS.g, COLOR_TOP_STATS.b, 0.25F);
      }
   }

   public static Color getColorTopStatsRight(boolean isActive, boolean isHovered) {
      if (isActive) {
         return COLOR_TOP_STATS_RIGHT_ACTIVE;
      } else {
         return isHovered ? COLOR_TOP_STATS_RIGHT_HOVER : COLOR_TOP_STATS_RIGHT;
      }
   }

   public static Color getColorTopStatsHover(boolean isActive, boolean isHovered) {
      if (isActive) {
         return COLOR_TOP_STATS_ACTIVE;
      } else {
         return isHovered ? COLOR_TOP_STATS : COLOR_TOP_STATS_HOVER;
      }
   }

   public static Color getColorTopStats2(boolean isActive, boolean isHovered) {
      if (isActive) {
         return COLOR_TOP_STATS_ACTIVE;
      } else {
         return isHovered ? COLOR_TOP_STATS_HOVER : COLOR_TOP_STATS2;
      }
   }

   public static Color getColorTopStats3(boolean isActive, boolean isHovered) {
      if (isActive) {
         return COLOR_TOP_STATS_ACTIVE;
      } else {
         return isHovered ? COLOR_TOP_STATS_HOVER : COLOR_TOP_STATS3;
      }
   }

   public static Color getColorTopStats4(boolean isActive, boolean isHovered) {
      if (isActive) {
         return COLOR_TOP_STATS_ACTIVE;
      } else {
         return isHovered ? COLOR_TOP_STATS_HOVER : COLOR_TOP_STATS4;
      }
   }

   public static Color getColorTopStats5(boolean isActive, boolean isHovered) {
      if (isActive) {
         return COLOR_TOP_STATS_ACTIVE;
      } else {
         return isHovered ? COLOR_TOP_STATS_HOVER : BUTTON_TEXT;
      }
   }

   public static Color getColorNeutral(boolean isActive, boolean isHovered) {
      if (isActive) {
         return COLOR_TEXT_MODIFIER_NEUTRAL_ACTIVE;
      } else {
         return isHovered ? COLOR_TEXT_MODIFIER_NEUTRAL_HOVER : COLOR_TEXT_MODIFIER_NEUTRAL;
      }
   }

   public static Color getColorPositive(boolean isActive, boolean isHovered) {
      if (isActive) {
         return COLOR_TEXT_MODIFIER_POSITIVE_ACTIVE;
      } else {
         return isHovered ? COLOR_TEXT_MODIFIER_POSITIVE_HOVER : COLOR_TEXT_MODIFIER_POSITIVE;
      }
   }

   public static Color getColorPopulation(boolean isActive, boolean isHovered) {
      if (isActive) {
         return COLOR_POPULATION_ACTIVE;
      } else {
         return isHovered ? COLOR_POPULATION_HOVER : COLOR_POPULATION;
      }
   }

   public static Color getColorEconomy(boolean isActive, boolean isHovered) {
      if (isActive) {
         return COLOR_TEXT_ECONOMY_ACTIVE;
      } else {
         return isHovered ? COLOR_TEXT_ECONOMY_HOVER : COLOR_TEXT_ECONOMY;
      }
   }

   public static Color getColorNegative(boolean isActive, boolean isHovered) {
      if (isActive) {
         return COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE;
      } else {
         return isHovered ? COLOR_TEXT_MODIFIER_NEGATIVE_HOVER : COLOR_TEXT_MODIFIER_NEGATIVE;
      }
   }

   public static Color getPopulationColor(int nData, float nAlpha) {
      return CFG.getColorStep(COLOR_POPULATION_VIEW[3], COLOR_POPULATION_VIEW[8], Math.min(nData, 99) % 100, 100, nAlpha);
   }

   public static Color getWhiteBlackColor(int nData, float nAlpha) {
      return CFG.getColorStep(Color.WHITE, Color.BLUE, Math.min(nData, 99) % 100, 100, nAlpha);
   }

   public static Color getEconomyColor(int nData, float nAlpha) {
      return CFG.getColorStep(COLOR_ECONOMY[2], COLOR_ECONOMY[6], Math.min(nData, 99) % 100, 100, nAlpha);
   }

   public static Color getProvinceIncomeColor(int nData, float nAlpha) {
      return CFG.getColorStep(COLOR_PROVINCE_INCOME[0], COLOR_PROVINCE_INCOME[1], Math.min(99, nData) % 100, 100, nAlpha);
   }

   public static Color getProvinceInfrastructureColor(int nData, float nAlpha) {
      return CFG.getColorStep(COLOR_PROVINCE_INFRASTRUCTURE[0], COLOR_PROVINCE_INFRASTRUCTURE[1], Math.min(99, nData) % 100, 100, nAlpha);
   }

   public static Color getProvinceProvinceValueColor(int nData, float nAlpha) {
      return CFG.getColorStep(COLOR_PROVINCE_PROVINCE_VALUE[0], COLOR_PROVINCE_PROVINCE_VALUE[1], Math.min(99, nData) % 100, 100, nAlpha);
   }

   public static Color getProvinceRedColor(int nData, float nAlpha) {
      return CFG.getColorStep(COLOR_PROVINCE_RED[0], COLOR_PROVINCE_RED[1], Math.min(99, nData) % 100, 100, nAlpha);
   }

   public static Color getProvinceManpowerColor(int nData, float nAlpha) {
      return CFG.getColorStep(COLOR_PROVINCE_MANPOWER[0], COLOR_PROVINCE_MANPOWER[1], Math.min(99, nData) % 100, 100, nAlpha);
   }

   public static Color getProvinceTaxColor(int nData, float nAlpha) {
      return CFG.getColorStep(COLOR_PROVINCE_TAX[0], COLOR_PROVINCE_TAX[1], Math.min(99, nData) % 100, 100, nAlpha);
   }

   public static Color getProvinceEconomyColor(int nData, float nAlpha) {
      return CFG.getColorStep(COLOR_PROVINCE_ECONOMY[0], COLOR_PROVINCE_ECONOMY[1], Math.min(99, nData) % 100, 100, nAlpha);
   }

   public static Color getProvinceFortColor(int nData, float nAlpha) {
      return CFG.getColorStep(COLOR_PROVINCE_FORT[0], COLOR_PROVINCE_FORT[1], Math.min(99, nData) % 100, 100, nAlpha);
   }

   public static Color getProvinceDevastationColor(int nData, float nAlpha) {
      return nData == 0
         ? new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, nAlpha)
         : CFG.getColorStep(COLOR_PROVINCE_DEVASTATION[0], COLOR_PROVINCE_DEVASTATION[1], Math.min(99, nData) % 100, 100, nAlpha);
   }

   public static Color getProvinceLootColor(int nData, float nAlpha) {
      return nData == 0
         ? new Color(MapModeManager.PROVINCE_GRAY.r, MapModeManager.PROVINCE_GRAY.g, MapModeManager.PROVINCE_GRAY.b, nAlpha)
         : CFG.getColorStep(COLOR_PROVINCE_LOOT[0], COLOR_PROVINCE_LOOT[1], Math.min(99, nData) % 100, 100, nAlpha);
   }
}
