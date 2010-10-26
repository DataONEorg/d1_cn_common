/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package org.dataone.service.streaming.util;

/**
 * @author berkley
 * A class to handle string processing in streams.  This allows string
 * processing (substring, indexOf) without having to put the string into
 * memory.
 */
public class StreamUtil
{   
    /**
     * return an int array where element 0 is the starting position
     * of the search in source and element 1 is how many of the 
     * search characters were matched.  element 1 will normally be
     * search.length unless a portion of search was found at the beginning or end 
     * of source.  
     * @param search
     * @param source
     * @return
     */
    public static int[] lookForMatch(String search, String source)
    {
        int result[] = new int[2];
        int beginResult = checkForSearchAtBeginningOfSource(search, source);
        if(source.indexOf(search) != -1)
        { //simple case
            result[0] = source.indexOf(search);
            result[1] = search.length();
            return result;
        }
        else if(beginResult != -1)
        { //find substring of search at the beginning of the source
            result[0] = 0;
            result[1] = beginResult;
            return result;
        }
        else
        { //find substring of search at the end of the source
            int searchStartIndex = 0;
            //System.out.println("search: " + search);
            //System.out.println("source: " + source);
            for(int i=0; i<source.length(); i++)
            {
                String searchChar = search.substring(searchStartIndex, searchStartIndex + 1);
                String sourceChar = source.substring(i, i+1);
                //System.out.println("searchStartIndex: " + searchStartIndex);
                //System.out.println("search char: " + searchChar);
                //System.out.println("sourceChar: " + sourceChar);
                if(sourceChar.equals(searchChar))
                {
                    searchStartIndex++;
                    if(searchStartIndex == search.length())
                    {
                        result[0] = searchStartIndex - search.length();
                        result[1] = searchStartIndex;
                        return result;
                    }
                    
                    if(i == source.length() - 1)
                    {
                        result[0] = source.length() - searchStartIndex;
                        result[1] = searchStartIndex;
                        return result;
                    }
                }
                else
                {
                    searchStartIndex = 0;
                }
            }
        }
        
        result[0] = -1;
        result[1] = -1;
        return result;
    }
    
    /**
     * looks for search at the beginning of source.  If any part of search is found
     * return the number of characters that match.  If nothing is found, return -1
     * @param search
     * @param source
     * @return
     * 
     * ndary=\"asdflkj\" asdf asd f asdf asdf
     * arxlkasjdf asdf sdf
     * 
     */
    private static int checkForSearchAtBeginningOfSource(String search, String source)
    {
        int numfound = -1;
        for(int i=0; i<search.length(); i++)
        {
            String searchChar = search.substring(i, i+1);
            String sourceChar = source.substring(0, 1);
            //System.out.println("searchChar: " + searchChar);
            //System.out.println("sourceChar: " + sourceChar);
            if(sourceChar.equals(searchChar))
            {
                boolean match = true;
                //System.out.println("first char matched");
                for(int j=0; j<search.length() - i; j++)
                {
                    sourceChar = source.substring(j, j+1);
                    searchChar = search.substring(i+j, i+j+1);
                    //System.out.println("  searchChar: " + searchChar);
                    //System.out.println("  sourceChar: " + sourceChar);
                    
                    if(!sourceChar.equals(searchChar))
                    {
                        match = false;
                        break;
                    }
                }
                if(match)
                {
                    //System.out.println("MATCH");
                    return search.length() - i;
                }
            }
        }
        
        return numfound;
    }
}
