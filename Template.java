import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
public class Template{
    public void add_obj(Component comp, Container cont, GridBagLayout layout, GridBagConstraints gbc, int gridx, int gridy){
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        layout.setConstraints(comp,gbc);
        cont.add(comp);
    }
}

