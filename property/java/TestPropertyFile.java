//import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.antlr.v4.misc.OrderedHashMap;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

class PropertyFilePrinter extends PropertyFileParser {
    public PropertyFilePrinter(TokenStream input) {
        super(input);
    }

    void defineProperty(Token name, Token value) {
        System.out.println(name.getText()+"<=>"+value.getText());
    }
}

public class TestPropertyFile {
    public static class PropertyFileMapper extends PropertyFileBaseListener {
        Map<String, String> properties = new OrderedHashMap<String, String>();
        public void exitProp(PropertyFileParser.PropContext ctx) {
            // prop: ID '=' STRING '\n' ;
            String id = ctx.ID().getText();
            String val = ctx.STRING().getText();
            properties.put(id, val);
        }
    }

    public static class PropertyFileLoader extends PropertyFileBaseVisitor<Void> {
        public Void visitProp(PropertyFileParser.PropContext ctx) {
            // prop: ID '=' STRING '\n' ;
            String id = ctx.ID().getText();
            String val = ctx.STRING().getText();
            System.out.println("id: " + id + ", value: " + val);
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        // InputStream is = new FileInputStream("property.in");
        // ANTLRInputStream input = new ANTLRInputStream(is);
        CharStream input = CharStreams.fromFileName("property.in");
        PropertyFileLexer lexer = new PropertyFileLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        PropertyFilePrinter parser = new PropertyFilePrinter(tokens);
        ParseTree tree = parser.file();

        ParseTreeWalker walker = new ParseTreeWalker();
        PropertyFileMapper mapper = new PropertyFileMapper();
        walker.walk(mapper, tree);
        System.out.println(mapper.properties);

        PropertyFileLoader loader = new PropertyFileLoader();
        loader.visit(tree);
    }
}
