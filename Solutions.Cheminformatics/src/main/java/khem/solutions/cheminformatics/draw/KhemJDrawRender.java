package khem.solutions.cheminformatics.draw;

import java.net.URL;

import com.symyx.draw.JDrawComponent;
import com.symyx.draw.JDrawPreferences;
import com.symyx.draw.Renderer;



public class KhemJDrawRender  extends KhemJDrawApplet
implements JDrawComponent
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5596983453718694400L;
	public KhemJDrawRender()
    {
        super(new Renderer());
        jsOnMouseLeftClick = null;
        jsOnMouseLeftDoubleClick = null;
        //TODO: ((Renderer)wrapped).addMouseListener(new _cls1());
    }

    public void init()
    {
        super.init();
        String s = getParameter("OnMouseLeftClickJS");
        if(s != null)
            setOnMouseLeftClickJS(s);
        String s1 = getParameter("OnMouseLeftDoubleClickJS");
        if(s1 != null)
            setOnMouseLeftDoubleClickJS(s1);
    }

    public JDrawPreferences preferences()
    {
        return super.preferences();
    }

    public void clearMolecule()
    {
        super.clearMolecule();
    }

    public String getMolString()
    {
        return super.getMolString();
    }

    public String getMolString(String s)
    {
        return super.getMolString(s);
    }

    public void setMolString(String s)
    {
        super.setMolString(s);
    }

    public void setMolecule(URL url)
    {
        super.setMolecule(url);
    }

    public String getRxnString()
    {
        return super.getRxnString();
    }

    public String getRxnString(String s)
    {
        return super.getRxnString(s);
    }

    public void setRxnString(String s)
    {
        super.setRxnString(s);
    }

    public String getChimeString()
    {
        return super.getChimeString();
    }

    public void setChimeString(String s)
    {
        super.setChimeString(s);
    }

    @SuppressWarnings("deprecation")
	public boolean setHydrogenDisplayMode(int i)
    {
        return super.setHydrogenDisplayMode(i);
    }

    public void setSubscriptFontRatio(double d)
    {
        super.setSubscriptFontRatio(d);
    }

    public void setDefaultBondLength(double d)
    {
        super.setDefaultBondLength(d);
    }

    public void setLabelHeight(double d)
    {
        super.setLabelHeight(d);
    }

    public void setDefaultFontSize(double d)
    {
        super.setDefaultFontSize(d);
    }

    public boolean isLoaded()
    {
        return super.isLoaded();
    }

    public static String getVersion()
    {
    	return "1.0";
        //return JDrawInternalUtils.getVersionString();
    }

    public void setOnMouseLeftClickJS(String s)
    {
        jsOnMouseLeftClick = s;
    }

    public void setOnMouseLeftDoubleClickJS(String s)
    {
        jsOnMouseLeftDoubleClick = s;
    }

    //public volatile void start()
    public void start()
    {
        super.start();
    }

    
    /**
	 * @return the jsOnMouseLeftClick
	 */
	public String getJsOnMouseLeftClick()
	{
		return jsOnMouseLeftClick;
	}

	/**
	 * @param jsOnMouseLeftClick the jsOnMouseLeftClick to set
	 */
	public void setJsOnMouseLeftClick(String jsOnMouseLeftClick)
	{
		this.jsOnMouseLeftClick = jsOnMouseLeftClick;
	}

	/**
	 * @return the jsOnMouseLeftDoubleClick
	 */
	public String getJsOnMouseLeftDoubleClick()
	{
		return jsOnMouseLeftDoubleClick;
	}

	/**
	 * @param jsOnMouseLeftDoubleClick the jsOnMouseLeftDoubleClick to set
	 */
	public void setJsOnMouseLeftDoubleClick(String jsOnMouseLeftDoubleClick)
	{
		this.jsOnMouseLeftDoubleClick = jsOnMouseLeftDoubleClick;
	}


	/**
	 * @return the self
	 */
	public KhemJDrawRender getSelf()
	{
		return self;
	}


	private final KhemJDrawRender self = this;
    private String jsOnMouseLeftClick;
    private String jsOnMouseLeftDoubleClick;


}
