package graphs;

/**
 *
 * In this class some usefull utilities functions are contained 
 * 
 * @author zabetak
 */
public class Utilities {

    /**
     * Returns the local part of a uri. The part after the character '#'
     * 
     * @param fulluri - the fulluri that  
     * @return the local part of the uri
     */
    public static String extractLocalPartFromURI(String fulluri){
        int lindexof = fulluri.toString().lastIndexOf("#");
        return fulluri.toString().substring(lindexof+1); 
    }

    /**
     * Returns the namespace part of a uri. The part beffore the character '#'
     *
     * @param fulluri - the fulluri that
     * @return the namespace part of the uri
     */
    public static String extractNamespacePartFromURI(String fulluri){
        int lindexof = fulluri.toString().indexOf("#");
        return fulluri.toString().substring(0,lindexof);
    }
}
