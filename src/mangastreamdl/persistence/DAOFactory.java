/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.persistence;

/**
 * @author Grigor Riskov - riskogri@fit.cvut.cz
 */
public class DAOFactory
{

    // Factory Method implementation.
    public static DAO getDAO(DAOType type) throws NoSuchTypeException
    {
        DAO dao;
        switch (type)
        {
            case XML:
                dao = XMLDAO.getInstance();
                break;
            case PTXT:
                dao = PlainTextDAO.getInstance();
                break;
            default:
                throw new NoSuchTypeException();
        }
        return dao;
    }

}
