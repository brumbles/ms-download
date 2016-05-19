/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.business.parser;

import javax.swing.text.html.HTMLEditorKit.Parser;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import java.io.IOException;
import java.io.Reader;

/**
 * @author Grigor Riskov - grigor@riskov.cz
 */
public class HTMLParser extends Parser
{

    @Override
    public void parse(Reader r, ParserCallback cb, boolean ignoreCharSet) throws IOException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public class PCB extends ParserCallback
    {

    }

}
