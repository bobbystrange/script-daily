package org.dreamcat.daily.script;

import java.util.List;
import lombok.Data;

/**
 * @author Jerry Will
 * @since 2021/6/15
 */
@Data
public class ParamSchema {

    String name;
    Class<?> type;
    String description;

    List<ParamSchema> properties; // for object
    ParamSchema items; // for array
}
