package enums;

public enum PatternMode {
	BLACK ("All Black", ' ', new String[]{}),
    SOLID ("Set", '0', new String[]{"Brightness"}),
    FADE_ONEWAY_RECALC ("Fade Variable", '1', new String[]{"Brightness", "Speed"}),
    FADE_ONEWAY_FIXED ("Fade Fixed", '2', new String[]{"Brightness", "Speed"}),
    SINGLE ("One Pulse", '7', new String[]{"Brightness", "Attack", "Decay"}),
    BLINK ("Blink Steady", '3', new String[]{"Brightness", "Speed", "Duty"}),
    BLINK_RANDOM ("Blink Random", '5', new String[]{"Brightness", "Speed", "Duty"}),
    PULSE ("Pulse Steady", '4', new String[]{"Brightness", "Speed", "Range"}),
    PULSE_RANDOM ("Pulse Random", '6', new String[]{"Brightness", "Speed", "Range"}),
    RGB_SOLID ("RGB: Solid", 'D', new String[]{"Brightness", "Saturation"}),
    RGB_SPARKLE ("RGB: Sparkle", 'A', new String[]{"Brightness", "Speed"}),
    RGB_CYLON ("RGB: Cylon", 'B', new String[]{"Brightness", "Speed"});
    
    private String label;
    private char commandValue;
    private String[] args;

    PatternMode (String label, char commandValue, String []args) {
        this.label = label;
        this.commandValue = commandValue;
        this.args = args;
    }
    public String getLabel() { return label; }
    public char getCommandValue() { return commandValue; }
    public String arg1() { if(args.length >= 1) return args[0]; else return null; }
    public String arg2() { if(args.length >= 2) return args[1]; else return null; }
    public String arg3() { if(args.length >= 3) return args[2]; else return null; }
    
    public String toString() { return label; }
}
