package khem.solutions.cheminformatics.draw;


import javax.swing.JApplet;


public class KhemJDrawApplet extends JApplet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2999311450236018455L;
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -1257640467538719885L;
//	KhemJDrawApplet(JDrawComponent jdrawcomponent)
//    {
//        isLoaded = false;
//        wrapped = jdrawcomponent;
//    }
//
//    public void init()
//    {
//        super.init();
//        getContentPane().add((Component)wrapped);
//    }
//
//    public void start()
//    {
//        super.start();
//        isLoaded = true;
//        parseAppletParameters(this);
//    }
//
//    protected boolean isLoaded()
//    {
//        return isLoaded;
//    }
//
//    void waitUntilLoaded()
//    {
//        while(!isLoaded()) 
//            try
//            {
//                Thread.sleep(10L);
//            }
//            catch(InterruptedException interruptedexception) { }
//    }
//
//    protected JDrawPreferences preferences()
//    {
//        waitUntilLoaded();
//        return wrapped.preferences();
//    }
//
//    protected void clearMolecule()
//    {
//        waitUntilLoaded();
//        wrapped.clearMolecule();
//    }
//
//    protected void setMolecule(URL url)
//    {
//        waitUntilLoaded();
//        wrapped.setMolecule(url);
//    }
//
//    protected String getMolString()
//    {
//        waitUntilLoaded();
//        return wrapped.getMolString();
//    }
//
//    protected String getMolString(String s)
//    {
//        waitUntilLoaded();
//        return wrapped.getMolString(s);
//    }
//
//    protected void setMolString(String s)
//    {
//        waitUntilLoaded();
//        wrapped.setMolString(s);
//    }
//
//    protected String getRxnString()
//    {
//        waitUntilLoaded();
//        return wrapped.getRxnString();
//    }
//
//    protected String getRxnString(String s)
//    {
//        waitUntilLoaded();
//        return wrapped.getRxnString(s);
//    }
//
//    protected void setRxnString(String s)
//    {
//        waitUntilLoaded();
//        wrapped.setRxnString(s);
//    }
//
//    protected String getChimeString()
//    {
//        waitUntilLoaded();
//        return wrapped.getChimeString();
//    }
//
//    protected void setChimeString(String s)
//    {
//        waitUntilLoaded();
//        wrapped.setChimeString(s);
//    }
//
//    /**
//     * @deprecated Method setHydrogenDisplayMode is deprecated
//     */
//
//    protected boolean setHydrogenDisplayMode(int i)
//    {
//        waitUntilLoaded();
//        preferences().setHydrogenDisplayMode(i);
//        return i >= 0 && i <= 4;
//    }
//
//    public void setSubscriptFontRatio(double d)
//    {
//        waitUntilLoaded();
//        preferences().setSubscriptFontRatio(d);
//    }
//
//    public void setDefaultBondLength(double d)
//    {
//        waitUntilLoaded();
//        preferences().setDefaultBondLength(d);
//    }
//
//    public void setLabelHeight(double d)
//    {
//        waitUntilLoaded();
//        preferences().setLabelHeight(d);
//    }
//
//    public void setDefaultFontSize(double d)
//    {
//        waitUntilLoaded();
//        double d1 = 1.0D;
//        double d2 = 1.0D;
//        if(wrapped.getClass() == com.symyx.draw.Editor.class)
//        {
//            d1 = ((Editor)wrapped).getDefaultScale();
//            d2 = ((Editor)wrapped).getOverallScale();
//        } else
//        if(wrapped.getClass() == com.symyx.draw.Renderer.class)
//        {
//            d1 = ((Renderer)wrapped).getDefaultScale();
//            d2 = ((Renderer)wrapped).getOverallScale();
//        }
//        double d3 = d * 0.0252D * (d1 / d2);
//        preferences().setLabelHeight(d3);
//    }
//
//    static boolean param2boolean(String s, boolean flag)
//    {
//        if(flag)
//            return !s.equalsIgnoreCase("false") && !s.equalsIgnoreCase("off") && !s.equalsIgnoreCase("no");
//        else
//            return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("on") || s.equalsIgnoreCase("yes");
//    }
//
//    static void parseAppletParameters(KhemJDrawApplet jdrawapplet)
//    {
//        String s = jdrawapplet.getParameter("defaultBondLength");
//        if(s != null && s.length() > 0)
//            try
//            {
//                jdrawapplet.preferences().setDefaultBondLength(Double.parseDouble(s));
//            }
//            catch(NumberFormatException numberformatexception)
//            {
//                System.out.println((new StringBuilder()).append("[JDraw] Unable to recognize \"defaultBondLength\" parameter value: ").append(s).toString());
//            }
//        String s1 = jdrawapplet.getParameter("labelHeight");
//        if(s1 != null && s1.length() > 0)
//            try
//            {
//                jdrawapplet.preferences().setLabelHeight(Double.parseDouble(s1));
//            }
//            catch(NumberFormatException numberformatexception1)
//            {
//                System.out.println((new StringBuilder()).append("[JDraw] Unable to recognize \"labelHeight\" parameter value: ").append(s1).toString());
//            }
//        String s2 = jdrawapplet.getParameter("bondLabelSize");
//        if(s2 != null && s2.length() > 0)
//            try
//            {
//                jdrawapplet.preferences().setBondLabelSize(Double.parseDouble(s2));
//            }
//            catch(NumberFormatException numberformatexception2)
//            {
//                System.out.println((new StringBuilder()).append("[JDraw] Unable to recognize \"bondLabelSize\" parameter value: ").append(s2).toString());
//            }
//        String s3 = jdrawapplet.getParameter("hydrogenDisplayMode");
//        if(s3 != null && s3.length() > 0)
//            try
//            {
//                jdrawapplet.preferences().setHydrogenDisplayMode(Integer.parseInt(s3));
//            }
//            catch(NumberFormatException numberformatexception3)
//            {
//                System.out.println((new StringBuilder()).append("[JDraw] Unable to recognize \"hydrogenDisplayMode\" parameter value: ").append(s3).toString());
//            }
//        String s4 = jdrawapplet.getParameter("polAtomDisplayMode");
//        if(s4 != null && s4.length() > 0)
//            try
//            {
//                jdrawapplet.preferences().setPolAtomDisplayMode(Integer.parseInt(s4));
//            }
//            catch(NumberFormatException numberformatexception4)
//            {
//                System.out.println((new StringBuilder()).append("[JDraw] Unable to recognize \"polAtomDisplayMode\" parameter value: ").append(s4).toString());
//            }
//        String s5 = jdrawapplet.getParameter("absStereoLabelText");
//        if(s5 != null && s5.length() > 0)
//            jdrawapplet.preferences().setAbsStereoLabelText(s5);
//        String s6 = jdrawapplet.getParameter("andStereoLabelText");
//        if(s6 != null && s6.length() > 0)
//            jdrawapplet.preferences().setAndStereoLabelText(s6);
//        String s7 = jdrawapplet.getParameter("orStereoLabelText");
//        if(s7 != null && s7.length() > 0)
//            jdrawapplet.preferences().setOrStereoLabelText(s7);
//        String s8 = jdrawapplet.getParameter("mixedStereoLabelText");
//        if(s8 != null && s8.length() > 0)
//            jdrawapplet.preferences().setMixedStereoLabelText(s8);
//        String s9 = jdrawapplet.getParameter("colorAtomsByType");
//        if(s9 != null && s9.length() > 0)
//            jdrawapplet.preferences().setColorAtomsByType(param2boolean(s9, true));
//        String s10 = jdrawapplet.getParameter("molString");
//        if(s10 != null && s10.length() > 0)
//        {
//            jdrawapplet.setMolString(s10.replaceAll("(?:\\\\r)?+\\\\n", "\n"));
//        } else
//        {
//            String s11 = jdrawapplet.getParameter("chimeString");
//            if(s11 != null && s11.length() > 0)
//            {
//                jdrawapplet.setChimeString(s11);
//            } else
//            {
//                String s12 = jdrawapplet.getParameter("moleculeURL");
//                if(s12 != null && s12.length() > 0)
//                    try
//                    {
//                        jdrawapplet.setMolecule(new URL(jdrawapplet.getDocumentBase(), s12));
//                    }
//                    catch(MalformedURLException malformedurlexception) { }
//            }
//        }
//    }
//
//    protected final JDrawComponent wrapped;
//    private boolean isLoaded;

}
