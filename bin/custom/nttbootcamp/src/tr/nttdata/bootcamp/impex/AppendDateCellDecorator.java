package tr.nttdata.bootcamp.impex;

import de.hybris.platform.util.CSVCellDecorator;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class AppendDateCellDecorator implements CSVCellDecorator {

    private static final Logger LOG = LoggerFactory.getLogger(AppendDateCellDecorator.class);

    @Override
    public String decorate(int position, Map<Integer, String> srcLine) {
        LOG.info("Entered cellDecorator at position {} with line {}", position, srcLine);
        String parsedValue = srcLine.get(position);
        if(StringUtils.isEmpty(parsedValue)){
            return "";
        }
        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return parsedValue + " " + sdf.format(new Date());
    }

}