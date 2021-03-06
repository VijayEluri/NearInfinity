// Near Infinity - An Infinity Engine Browser and Editor
// Copyright (C) 2001 - 2005 Jon Olav Hauglid
// See LICENSE.txt for license information

package infinity.resource.are;

import infinity.datatype.*;
import infinity.resource.AbstractStruct;
import infinity.resource.AddRemovable;

final class Variable extends AbstractStruct implements AddRemovable
{
  Variable() throws Exception
  {
    super(null, "Variable", new byte[84], 0);
  }

  Variable(AbstractStruct superStruct, byte buffer[], int offset) throws Exception
  {
    super(superStruct, "Variable", buffer, offset);
  }

  protected int read(byte buffer[], int offset) throws Exception
  {
    list.add(new TextString(buffer, offset, 32, "Name"));
    list.add(new Unknown(buffer, offset + 32, 8));
    list.add(new DecNumber(buffer, offset + 40, 4, "Value"));
    list.add(new Unknown(buffer, offset + 44, 40));
    return offset + 84;
  }
}

