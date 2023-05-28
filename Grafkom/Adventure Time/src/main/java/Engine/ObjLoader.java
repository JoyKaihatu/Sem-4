package Engine;

import org.joml.Vector2f;
import org.joml.Vector3f;
import Engine.*;
import java.io.*;
import java.nio.file.Path;

public class ObjLoader
{
    public static Model loadModel(File file) throws FileNotFoundException, IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Model result = new Model();
        String[] splitted;
        String line;
        float x, y, z;

        while((line = reader.readLine()) != null)
        {
            if(line.startsWith("v "))
            {
                splitted = line.split("\\s+");
                x = Float.valueOf(splitted[1]);
                y = Float.valueOf(splitted[2]);
                z = Float.valueOf(splitted[3]);
                result.vertices.add(new Vector3f(x, y, z));
            }
            else if(line.startsWith("vt "))
            {
                splitted = line.split("\\s+");
                x = Float.valueOf(splitted[1]);
                y = Float.valueOf(splitted[2]);
                result.texture.add(new Vector2f(x, y));
            }
            else if(line.startsWith("vn "))
            {
                splitted = line.split("\\s+");
                x = Float.valueOf(splitted[1]);
                y = Float.valueOf(splitted[2]);
                z = Float.valueOf(splitted[3]);
                result.normals.add(new Vector3f(x, y, z));
//                System.out.println(x + " " + y + " " + z);
            }
            else if(line.startsWith("f "))
            {
                splitted = line.split("\\s+");

                result.vertexIndices.add(Integer.parseInt(splitted[1].split("/")[0])-1);
                result.vertexIndices.add(Integer.parseInt(splitted[2].split("/")[0])-1);
                result.vertexIndices.add(Integer.parseInt(splitted[3].split("/")[0])-1);

//                result.indices.add(Integer.parseInt(splitted[1].split("/")[1]));
//                result.indices.add(Integer.parseInt(splitted[2].split("/")[1]));
//                result.indices.add(Integer.parseInt(splitted[3].split("/")[1]));

                result.normalIndices.add(Integer.parseInt(splitted[1].split("/")[2])-1);
                result.normalIndices.add(Integer.parseInt(splitted[2].split("/")[2])-1);
                result.normalIndices.add(Integer.parseInt(splitted[3].split("/")[2])-1);

//                System.out.println(result.indices.get(result.indices.size()-1));
            }
        }
        reader.close();
//        System.out.println("success");
//        System.out.println(result.vertices.size());
//        System.out.println(result.normals.size());
        return result;
    }
}