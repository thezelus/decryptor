import java.util.HashMap;
import java.util.Map;

public class Decryptor {

    String encryptedStringInput = "0A0B08090E0F0C0D02030001060704051A1B18191E1F1C1D12131011161714156A6B68696E6F6C6D6263606" +
            "1666764657A7B78797E7F7C7D72737071767774754A4B48494E4F4C4D42434041464744455A5B58595E5F5C5D5253505156575455AA" +
            "ABA8A9AEAFACADA2A3A0A1A6A7A4A5BABBB8B9BEBFBCBDB2B3B0B1B6B7B4B50A8B88898E8F8C8D82838081868784859A9B98999E9F9" +
            "C9D9293909196979495EAEBE8E9EEEFECEDE2E3E0E1E6E7E4E5FAFBF8F9FEFFFCFDF2F3F0F1F6F7F4F5CACBC8C9CECFCCCDC2C3C0C1" +
            "C6C7C4C5DADBD8D9DEDFDCDDD2D3D0D1D6D7D4D5";

    int startASCII = 32, endASCII = 255, mappedOutputLength = 2;

    public static void main(String[] args) {

        String realInput = "6B0A6F46484F584F5E420A6D43465E42454443C1462720594346435C584F440A5A4F44444B0A47C75843C1462720450A474F444F460A4B4D464B580A4F464F444B5E420B2720644B0749424B4F584F4E0A5A4B464B44074EC75843C1462720450A4D4B464B4E42584F474743440A4F444445584B5E420627206C4B445F43464559060A464F0A464344444B5E4245442720444F4C0A4B4F4B58060A59C70A444F4C0A4B4F4B5845440B";

        Decryptor decryptor = new Decryptor();
        System.out.println(decryptor.getDecryptedResult(realInput));
    }


    String getDecryptedResult(String encryptedData)
    {
        HashMap<String,Character> decryptionMap = populateDecryptionMap(encryptedStringInput, startASCII, endASCII, mappedOutputLength);
        String decryptedOutput = "";

        for(int i = 0; i<encryptedData.length(); i=i+mappedOutputLength)
        {
            String substring = encryptedData.substring(i, i + mappedOutputLength);
            Character character = decryptionMap.get(substring);
            if(character==null)
            {
                System.out.println("Key "+substring + " has no mapping. It's position is " + Integer.toString(i));
                character = (char)9;
            }
            decryptedOutput = decryptedOutput.concat(character.toString());
        }

        printDecryptionMap(decryptionMap);
        return decryptedOutput;
    }


    HashMap<String, Character> populateDecryptionMap(String inputData, int startASCII, int endASCII, int mappedOuputLength)
    {
        HashMap<String,Character> decryptionMap = new HashMap<String, Character>();

        if((endASCII-startASCII+1)*mappedOuputLength != encryptedStringInput.length())
            throw new RuntimeException("Decryption base logic incorrect. One ASCII doesn't map to "+Integer.toString(mappedOuputLength)+" string characters");

        for(int i = startASCII; i<=endASCII; i++)
        {
            int startIndex = (i - startASCII) * mappedOuputLength;
            String key = inputData.substring(startIndex, startIndex + mappedOuputLength);
            decryptionMap.put(key, (char)i);
        }

        return decryptionMap;
    }


    void printDecryptionMap(HashMap<String,Character> map)
    {
        for(String key: map.keySet())
        {
            System.out.println(key + "   -   " + map.get(key));
        }
    }
}
