// Near Infinity - An Infinity Engine Browser and Editor
// Copyright (C) 2001 - 2005 Jon Olav Hauglid
// See LICENSE.txt for license information

package infinity.resource.cre;

import infinity.NearInfinity;
import infinity.datatype.*;
import infinity.resource.*;
import infinity.resource.key.ResourceEntry;
import infinity.util.*;

import javax.swing.*;
import java.io.*;
import java.util.*;

public final class CreResource extends AbstractStruct implements Resource, HasAddRemovable, AddRemovable,
                                                                 HasDetailViewer
{
  private static final LongIntegerHashMap<String> m_magetype = new LongIntegerHashMap<String>();
  private static final LongIntegerHashMap<String> m_slotmask = new LongIntegerHashMap<String>();
  private static final LongIntegerHashMap<String> m_colorPlacement = new LongIntegerHashMap<String>();
  private static final LongIntegerHashMap<String> m_zoom = new LongIntegerHashMap<String>();
  private static final String s_flag[] = {"No flags set", "", "No corpse", "Permanent corpse",
                                          "Original class: Fighter",
                                          "Original class: Mage", "Original class: Cleric",
                                          "Original class: Thief", "Original class: Druid",
                                          "Original class: Ranger", "Fallen paladin", "Fallen ranger",
                                          "Export allowed", "Hide status", "", "Moving between areas", "Been in party",
                                          "", "", "", "", "", "", "", "", "Allegiance tracking",
                                          "General tracking", "Race tracking", "Class tracking",
                                          "Specifics tracking", "Gender tracking", "Alignment tracking",
                                          "Uninterruptible"};
  private static final String s_feats1[] = {
    "No feats selected", "Aegis of rime", "Ambidexterity", "Aqua mortis", "Armor prof", "Armored arcana",
    "Arterial strike", "Blind fight", "Bullheaded", "Cleave", "Combat casting", "Conundrum", "Crippling strike",
    "Dash", "Deflect arrows", "Dirty fighting", "Discipline", "Dodge", "Envenom weapon", "Exotic bastard",
    "Expertise", "Extra rage", "Extra shapeshifting", "Extra smiting", "Extra turning", "Fiendslayer",
    "Forester", "Great fortitude", "Hamstring", "Heretics bane", "Heroic inspiration", "Improved critical",
    "Improved evasion"};
  private static final String s_feats2[] = {
    "No feats selected", "Improved initiative", "Improved turning", "Iron will", "Lightning reflexes",
    "Lingering song", "Luck of heroes", "Martial axe", "Martial bow", "Martial flail", "Martial greatsword",
    "Martial hammer", "Martial large sword", "Martial polearm", "Maximized attacks", "Mercantile background",
    "Power attack", "Precise shot", "Rapid shot", "Resist poison", "Scion of storms", "Shield prof",
    "Simple crossbow", "Simple mace", "Simple missile", "Simple quarterstaff", "Simple small blade",
    "Slippery mind", "Snake blood", "Spell focus enchantment", "Spell focus evocation", "Spell focus necromancy",
    "Spell focus transmutation"};
  private static final String s_feats3[] = {
    "No feats selected", "Spell penetration", "Spirit of flame", "Strong back", "Stunning fist",
    "Subvocal casting",
    "Toughness", "Two weapon fighting", "Weapon finesse", "Wildshape boar", "Wildshape panther",
    "Wildshape shambler"};
  private static final String s_attacks[] = {"0", "1", "2", "3", "4", "5", "1/2", "3/2", "5/2", "7/2", "9/2"};
  private static final String s_sex[] = {"", "Male", "Female"};
  private static final String s_noyes[] = {"No", "Yes"};
  private static final String s_visible[] = {"Shown", "Hidden"};

  static
  {
    m_magetype.put((long)0x0000, "None");
    m_magetype.put((long)0x0040, "Abjurer");
    m_magetype.put((long)0x0080, "Conjurer");
    m_magetype.put((long)0x0100, "Diviner");
    m_magetype.put((long)0x0200, "Enchanter");
    m_magetype.put((long)0x0400, "Illusionist");
    m_magetype.put((long)0x0800, "Invoker");
    m_magetype.put((long)0x1000, "Necromancer");
    m_magetype.put((long)0x2000, "Transmuter");
    m_magetype.put((long)0x4000, "Generalist");

    m_slotmask.put(0L, "Yes");
    m_slotmask.put(65535L, "No");

    m_colorPlacement.put((long)0x80, "Metal");
    m_colorPlacement.put((long)0x81, "Metal (hologram)");
    m_colorPlacement.put((long)0x82, "Metal (pulsate)");
    m_colorPlacement.put((long)0x83, "Metal (hologram/pulsate)");
    m_colorPlacement.put((long)0x90, "Minor cloth");
    m_colorPlacement.put((long)0x91, "Minor cloth (hologram)");
    m_colorPlacement.put((long)0x92, "Minor cloth (pulsate)");
    m_colorPlacement.put((long)0x93, "Minor cloth (hologram/pulsate)");
    m_colorPlacement.put((long)0xA0, "Main cloth");
    m_colorPlacement.put((long)0xA1, "Main cloth (hologram)");
    m_colorPlacement.put((long)0xA2, "Main cloth (pulsate)");
    m_colorPlacement.put((long)0xA3, "Main cloth (hologram/pulsate)");
    m_colorPlacement.put((long)0xB0, "Skin");
    m_colorPlacement.put((long)0xB1, "Skin (hologram)");
    m_colorPlacement.put((long)0xB2, "Skin (pulsate)");
    m_colorPlacement.put((long)0xB3, "Skin (hologram/pulsate)");
    m_colorPlacement.put((long)0xC0, "Leather");
    m_colorPlacement.put((long)0xC1, "Leather (hologram)");
    m_colorPlacement.put((long)0xC2, "Leather (pulsate)");
    m_colorPlacement.put((long)0xC3, "Leather (hologram/pulsate)");
    m_colorPlacement.put((long)0xD0, "Armor");
    m_colorPlacement.put((long)0xD1, "Armor (hologram)");
    m_colorPlacement.put((long)0xD2, "Armor (pulsate)");
    m_colorPlacement.put((long)0xD3, "Armor (hologram/pulsate)");
    m_colorPlacement.put((long)0xE0, "Hair");
    m_colorPlacement.put((long)0xE1, "Hair (hologram)");
    m_colorPlacement.put((long)0xE2, "Hair (pulsate)");
    m_colorPlacement.put((long)0xE3, "Hair (hologram/pulsate)");
    m_colorPlacement.put((long)0x00, "Not used");

    m_zoom.put(0L, "No");
    m_zoom.put(65535L, "Yes");
  }

  public static void addScriptName(Set<String> scriptNames, byte buffer[])
  {
    String signature = new String(buffer, 0, 4);
    String scriptName = "";
    if (signature.equalsIgnoreCase("CRE ")) {
      String version = new String(buffer, 4, 4);
      if (version.equalsIgnoreCase("V1.0"))
        scriptName = Byteconvert.convertString(buffer, 640, 32);
      else if (version.equalsIgnoreCase("V1.1") || version.equalsIgnoreCase("V1.2"))
        scriptName = Byteconvert.convertString(buffer, 804, 32);
      else if (version.equalsIgnoreCase("V2.2"))
        scriptName = Byteconvert.convertString(buffer, 916, 32);
      else if (version.equalsIgnoreCase("V9.0"))
        scriptName = Byteconvert.convertString(buffer, 744, 32);
      if (scriptName.equals("") || scriptName.equalsIgnoreCase("None"))
        ;
      // Apparently script name is the only thing that matters
//        scriptName = entry.toString().substring(0, entry.toString().length() - 4);
      else
        scriptNames.add(scriptName.toLowerCase().replaceAll(" ", ""));
    }
  }

  private static void adjustEntryOffsets(AbstractStruct struct, int amount)
  {
    for (int i = 0; i < struct.getRowCount(); i++) {
      StructEntry structEntry = struct.getStructEntryAt(i);
      structEntry.setOffset(structEntry.getOffset() + amount);
      if (structEntry instanceof AbstractStruct)
        adjustEntryOffsets((AbstractStruct)structEntry, amount);
    }
  }

  public static void convertCHRtoCRE(ResourceEntry resourceEntry)
  {
    if (!resourceEntry.getExtension().equalsIgnoreCase("CHR"))
      return;
    String resourcename = resourceEntry.toString();
    resourcename = resourcename.substring(0, resourcename.lastIndexOf(".")) + ".CRE";
    JFileChooser chooser = new JFileChooser(ResourceFactory.getRootDir());
    chooser.setDialogTitle("Convert CHR to CRE");
    chooser.setSelectedFile(new File(resourcename));
    if (chooser.showSaveDialog(NearInfinity.getInstance()) == JFileChooser.APPROVE_OPTION) {
      File output = chooser.getSelectedFile();
      if (output.exists()) {
        String options[] = {"Overwrite", "Cancel"};
        int result = JOptionPane.showOptionDialog(NearInfinity.getInstance(), output + " exists. Overwrite?",
                                                  "Save resource", JOptionPane.YES_NO_OPTION,
                                                  JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        if (result == 1) return;
      }
      try {
        CreResource crefile = (CreResource)ResourceFactory.getResource(resourceEntry);
        while (!crefile.getStructEntryAt(0).toString().equals("CRE "))
          crefile.list.remove(0);
//        convertToSemiStandard(crefile); // ToDo:Enable conversion again
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(output));
        crefile.write(bos);
        bos.close();
        JOptionPane.showMessageDialog(NearInfinity.getInstance(), "File saved to " + output,
                                      "Conversion complete", JOptionPane.INFORMATION_MESSAGE);
      } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(NearInfinity.getInstance(), "Errors during conversion",
                                      "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private static void convertToSemiStandard(CreResource crefile)
  {
    if (!crefile.getStructEntryAt(1).toString().equals("V1.0")) {
      System.err.println("Conversion to semi-standard aborted: Unsupported CRE version");
      return;
    }

    // Order:
    //  KnownSpell
    //  SpellMemorizationInfo
    //  MemorizedSpell
    //  Effects
    //  Items
    //  ItemSlots

    // Adjust offsets first - Size of CHR header = 0x64
    adjustEntryOffsets(crefile, -0x64);

    SectionOffset knownspells_offset = (SectionOffset)crefile.getAttribute("Known spells offset");
    SectionOffset memspellinfo_offset = (SectionOffset)crefile.getAttribute("Memorization info offset");
    SectionOffset memspells_offset = (SectionOffset)crefile.getAttribute("Memorized spells offset");
    SectionOffset itemslots_offset = (SectionOffset)crefile.getAttribute("Item slots offset");
    SectionOffset items_offset = (SectionOffset)crefile.getAttribute("Items offset");
    SectionOffset effects_offset = (SectionOffset)crefile.getAttribute("Effects offset");

    int indexStructs = crefile.getIndexOf(effects_offset) + 3; // Start of non-permanent section
    List<StructEntry> newlist = new ArrayList<StructEntry>(crefile.getRowCount());
    for (int i = 0; i < indexStructs; i++)
      newlist.add(crefile.getStructEntryAt(i));

    int offsetStructs = 0x2d4;
    knownspells_offset.setValue(offsetStructs);
    offsetStructs = copyStruct(crefile.list, newlist, indexStructs, offsetStructs, KnownSpells.class);

    memspellinfo_offset.setValue(offsetStructs);
    offsetStructs = copyStruct(crefile.list, newlist, indexStructs, offsetStructs, SpellMemorization.class);

    memspells_offset.setValue(offsetStructs);
    offsetStructs = copyStruct(crefile.list, newlist, indexStructs, offsetStructs, MemorizedSpells.class);

    effects_offset.setValue(offsetStructs);
    offsetStructs =
    copyStruct(crefile.list, newlist, indexStructs, offsetStructs, effects_offset.getSection());

    items_offset.setValue(offsetStructs);
    offsetStructs = copyStruct(crefile.list, newlist, indexStructs, offsetStructs, Item.class);

    itemslots_offset.setValue(offsetStructs);
    offsetStructs = copyStruct(crefile.list, newlist, indexStructs, offsetStructs, DecNumber.class);
    copyStruct(crefile.list, newlist, indexStructs, offsetStructs, Unknown.class);

    crefile.list = newlist;
  }

  private static int copyStruct(List<StructEntry> oldlist, List<StructEntry> newlist,
                                int indexStructs, int offsetStructs, Class copyClass)
  {
    for (int i = indexStructs; i < oldlist.size(); i++) {
      StructEntry structEntry = oldlist.get(i);
      if (structEntry.getClass() == copyClass) {
        structEntry.setOffset(offsetStructs);
        if (structEntry instanceof AbstractStruct)
          ((AbstractStruct)structEntry).realignStructOffsets();
        offsetStructs += structEntry.getSize();
        newlist.add(structEntry);
      }
    }
    return offsetStructs;
  }

  public static String getSearchString(byte buffer[])
  {
    String signature = new String(buffer, 0, 4);
    if (signature.equalsIgnoreCase("CHR "))
      return new String(buffer, 8, 32);
    String name = new StringRef(buffer, 8, "").toString().trim();
    String shortname = new StringRef(buffer, 12, "").toString().trim();
    if (name.equals(shortname))
      return name;
    return name + " - " + shortname;
  }

  public CreResource(ResourceEntry entry) throws Exception
  {
    super(entry);
  }

  public CreResource(AbstractStruct superStruct, String name, byte data[], int startoffset) throws Exception
  {
    super(superStruct, name, data, startoffset);
  }

// --------------------- Begin Interface HasAddRemovable ---------------------

  public AddRemovable[] getAddRemovables() throws Exception
  {
    DecNumber effectFlag = (DecNumber)getAttribute("Effect flag");
    if (ResourceFactory.getGameID() == ResourceFactory.ID_ICEWIND2) {
      if (effectFlag.getValue() == 1)
        return new AddRemovable[]{new Item(), new Effect2()};
      else
        return new AddRemovable[]{new Item(), new Effect()};
    }
    else {
      if (effectFlag.getValue() == 1)
        return new AddRemovable[]{new Item(), new Effect2(), new KnownSpells(), new SpellMemorization()};
      else
        return new AddRemovable[]{new Item(), new Effect(), new KnownSpells(), new SpellMemorization()};
    }
  }

// --------------------- End Interface HasAddRemovable ---------------------


// --------------------- Begin Interface HasDetailViewer ---------------------

  public JComponent getDetailViewer()
  {
    return new Viewer(this);
  }

// --------------------- End Interface HasDetailViewer ---------------------


// --------------------- Begin Interface Writeable ---------------------

  public void write(OutputStream os) throws IOException
  {
    super.writeFlatList(os);
  }

// --------------------- End Interface Writeable ---------------------

  protected void datatypeAdded(AddRemovable datatype)
  {
    updateOffsets(datatype, datatype.getSize());
    if (datatype instanceof SpellMemorization)
      updateMemorizedSpells();
  }

  protected void datatypeAddedInChild(AbstractStruct child, AddRemovable datatype)
  {
    updateOffsets(datatype, datatype.getSize());
    if (datatype instanceof MemorizedSpells)
      updateMemorizedSpells();
    super.datatypeAddedInChild(child, datatype);
  }

  protected void datatypeRemoved(AddRemovable datatype)
  {
    updateOffsets(datatype, -datatype.getSize());
    if (datatype instanceof SpellMemorization)
      updateMemorizedSpells();
  }

  protected void datatypeRemovedInChild(AbstractStruct child, AddRemovable datatype)
  {
    updateOffsets(datatype, -datatype.getSize());
    if (datatype instanceof MemorizedSpells)
      updateMemorizedSpells();
    super.datatypeRemovedInChild(child, datatype);
  }

  protected int read(byte buffer[], int offset) throws Exception
  {
    setExtraOffset(getExtraOffset() + offset);
    TextString signature = new TextString(buffer, offset, 4, "Signature");
    list.add(signature);
    TextString version = new TextString(buffer, offset + 4, 4, "Version");
    list.add(version);
    if (signature.toString().equalsIgnoreCase("CHR ")) {
      list.add(new TextString(buffer, offset + 8, 32, "Character name"));
      HexNumber structOffset = new HexNumber(buffer, offset + 40, 4, "CRE structure offset");
      list.add(structOffset);
      list.add(new HexNumber(buffer, offset + 44, 4, "CRE structure length"));
      if (version.toString().equalsIgnoreCase("V2.2"))
        list.add(new Unknown(buffer, offset + 48, structOffset.getValue() - 48));
      else {
        list.add(new IdsBitmap(buffer, offset + 48, 2, "Quick weapon slot 1", "SLOTS.IDS"));
        list.add(new IdsBitmap(buffer, offset + 50, 2, "Quick weapon slot 2", "SLOTS.IDS"));
        list.add(new IdsBitmap(buffer, offset + 52, 2, "Quick weapon slot 3", "SLOTS.IDS"));
        list.add(new IdsBitmap(buffer, offset + 54, 2, "Quick weapon slot 4", "SLOTS.IDS"));
        list.add(new HashBitmap(buffer, offset + 56, 2, "Show quick weapon 1?", m_slotmask));
        list.add(new HashBitmap(buffer, offset + 58, 2, "Show quick weapon 2?", m_slotmask));
        list.add(new HashBitmap(buffer, offset + 60, 2, "Show quick weapon 3?", m_slotmask));
        list.add(new HashBitmap(buffer, offset + 62, 2, "Show quick weapon 4?", m_slotmask));
        list.add(new ResourceRef(buffer, offset + 64, "Quick spell 1", "SPL"));
        list.add(new ResourceRef(buffer, offset + 72, "Quick spell 2", "SPL"));
        list.add(new ResourceRef(buffer, offset + 80, "Quick spell 3", "SPL"));
        list.add(new IdsBitmap(buffer, offset + 88, 2, "Quick item slot 1", "SLOTS.IDS"));
        list.add(new IdsBitmap(buffer, offset + 90, 2, "Quick item slot 2", "SLOTS.IDS"));
        list.add(new IdsBitmap(buffer, offset + 92, 2, "Quick item slot 3", "SLOTS.IDS"));
        list.add(new HashBitmap(buffer, offset + 94, 2, "Show quick item 1?", m_slotmask));
        list.add(new HashBitmap(buffer, offset + 96, 2, "Show quick item 2?", m_slotmask));
        list.add(new HashBitmap(buffer, offset + 98, 2, "Show quick item 3?", m_slotmask));
      }
      offset = structOffset.getValue();
      list.add(new TextString(buffer, offset, 4, "Signature 2"));
      version = new TextString(buffer, offset + 4, 4, "Version 2");
      list.add(version);
      setExtraOffset(getExtraOffset() + structOffset.getValue());
    }
    offset += 8;
    if (version.toString().equalsIgnoreCase("V2.2"))
      return readIWD2(buffer, offset);
    return readOther(version.toString(), buffer, offset);
  }

  ////////////////////////
  // Icewind Dale 2
  ////////////////////////

  private int readIWD2(byte buffer[], int offset) throws Exception
  {
    list.add(new StringRef(buffer, offset, "Name"));
    list.add(new StringRef(buffer, offset + 4, "Tooltip"));
    list.add(new Flag(buffer, offset + 8, 4, "Flags", new String[]{"No flags set"}));
    list.add(new DecNumber(buffer, offset + 12, 4, "XP value"));
    list.add(new DecNumber(buffer, offset + 16, 4, "XP"));
    list.add(new DecNumber(buffer, offset + 20, 4, "Gold"));
    list.add(new IdsFlag(buffer, offset + 24, 4, "Status", "STATE.IDS"));
    list.add(new DecNumber(buffer, offset + 28, 2, "Current HP"));
    list.add(new DecNumber(buffer, offset + 30, 2, "Maximum HP"));
    list.add(new IdsBitmap(buffer, offset + 32, 4, "Animation", "ANIMATE.IDS"));
//    list.add(new Unknown(buffer, offset + 34, 2));
    list.add(new ColorValue(buffer, offset + 36, 1, "Metal color"));
    list.add(new ColorValue(buffer, offset + 37, 1, "Minor color"));
    list.add(new ColorValue(buffer, offset + 38, 1, "Major color"));
    list.add(new ColorValue(buffer, offset + 39, 1, "Skin color"));
    list.add(new ColorValue(buffer, offset + 40, 1, "Leather color"));
    list.add(new ColorValue(buffer, offset + 41, 1, "Armor color"));
    list.add(new ColorValue(buffer, offset + 42, 1, "Hair color"));
    DecNumber effect_flag = new DecNumber(buffer, offset + 43, 1, "Effect flag");
    list.add(effect_flag);
    list.add(new ResourceRef(buffer, offset + 44, "Small portrait", "BMP"));
    list.add(new ResourceRef(buffer, offset + 52, "Large portrait", "BMP"));
    list.add(new DecNumber(buffer, offset + 60, 1, "Reputation"));
    list.add(new Unknown(buffer, offset + 61, 1));
    list.add(new DecNumber(buffer, offset + 62, 2, "AC"));
    list.add(new DecNumber(buffer, offset + 64, 2, "Bludgeoning AC modifier"));
    list.add(new DecNumber(buffer, offset + 66, 2, "Missile AC modifier"));
    list.add(new DecNumber(buffer, offset + 68, 2, "Piercing AC modifier"));
    list.add(new DecNumber(buffer, offset + 70, 2, "Slashing AC modifier"));
    list.add(new Unknown(buffer, offset + 72, 1));
    list.add(new DecNumber(buffer, offset + 73, 1, "# attacks/round"));
    list.add(new DecNumber(buffer, offset + 74, 1, "Fortitude save"));
    list.add(new DecNumber(buffer, offset + 75, 1, "Reflex save"));
    list.add(new DecNumber(buffer, offset + 76, 1, "Will save"));
    list.add(new DecNumber(buffer, offset + 77, 1, "Fire resistance"));
    list.add(new DecNumber(buffer, offset + 78, 1, "Cold resistance"));
    list.add(new DecNumber(buffer, offset + 79, 1, "Electricity resistance"));
    list.add(new DecNumber(buffer, offset + 80, 1, "Acid resistance"));
    list.add(new DecNumber(buffer, offset + 81, 1, "Spell resistance"));
    list.add(new DecNumber(buffer, offset + 82, 1, "Magic fire resistance"));
    list.add(new DecNumber(buffer, offset + 83, 1, "Magic cold resistance"));
    list.add(new DecNumber(buffer, offset + 84, 1, "Slashing resistance"));
    list.add(new DecNumber(buffer, offset + 85, 1, "Bludgeoning resistance"));
    list.add(new DecNumber(buffer, offset + 86, 1, "Piercing resistance"));
    list.add(new DecNumber(buffer, offset + 87, 1, "Missile resistance"));
    list.add(new DecNumber(buffer, offset + 88, 1, "Magic damage resistance"));

    list.add(new Unknown(buffer, offset + 89, 4));
    list.add(new DecNumber(buffer, offset + 93, 1, "Fatigue"));
    list.add(new DecNumber(buffer, offset + 94, 1, "Intoxication"));
    list.add(new DecNumber(buffer, offset + 95, 1, "Luck"));
    list.add(new DecNumber(buffer, offset + 96, 1, "Turn undead level"));
    list.add(new Unknown(buffer, offset + 97, 33));

    list.add(new DecNumber(buffer, offset + 130, 1, "Total level"));
    list.add(new DecNumber(buffer, offset + 131, 1, "Barbarian level"));
    list.add(new DecNumber(buffer, offset + 132, 1, "Bard level"));
    list.add(new DecNumber(buffer, offset + 133, 1, "Cleric level"));
    list.add(new DecNumber(buffer, offset + 134, 1, "Druid level"));
    list.add(new DecNumber(buffer, offset + 135, 1, "Fighter level"));
    list.add(new DecNumber(buffer, offset + 136, 1, "Monk level"));
    list.add(new DecNumber(buffer, offset + 137, 1, "Paladin level"));
    list.add(new DecNumber(buffer, offset + 138, 1, "Ranger level"));
    list.add(new DecNumber(buffer, offset + 139, 1, "Rogue level"));
    list.add(new DecNumber(buffer, offset + 140, 1, "Sorcerer level"));
    list.add(new DecNumber(buffer, offset + 141, 1, "Wizard level"));
    list.add(new Unknown(buffer, offset + 142, 22));

    for (int i = 0; i < 64; i++)
      list.add(new StringRef(buffer, offset + 164 + 4 * i, "String"));

    list.add(new ResourceRef(buffer, offset + 420, "Team script", "BCS"));
    list.add(new ResourceRef(buffer, offset + 428, "Special script 1", "BCS"));
    list.add(new Unknown(buffer, offset + 436, 4));
    list.add(new Flag(buffer, offset + 440, 4, "Feats (1/3)", s_feats1));
    list.add(new Flag(buffer, offset + 444, 4, "Feats (2/3)", s_feats2));
    list.add(new Flag(buffer, offset + 448, 4, "Feats (3/3)", s_feats3));
    list.add(new Unknown(buffer, offset + 452, 12));
    list.add(new DecNumber(buffer, offset + 464, 1, "MW: Bow"));
    list.add(new DecNumber(buffer, offset + 465, 1, "SW: Crossbow"));
    list.add(new DecNumber(buffer, offset + 466, 1, "SW: Missile"));
    list.add(new DecNumber(buffer, offset + 467, 1, "MW: Axe"));
    list.add(new DecNumber(buffer, offset + 468, 1, "SW: Mace"));
    list.add(new DecNumber(buffer, offset + 469, 1, "MW: Flail"));
    list.add(new DecNumber(buffer, offset + 470, 1, "MW: Polearm"));
    list.add(new DecNumber(buffer, offset + 471, 1, "MW: Hammer"));
    list.add(new DecNumber(buffer, offset + 472, 1, "SW: Quarterstaff"));
    list.add(new DecNumber(buffer, offset + 473, 1, "MW: Greatsword"));
    list.add(new DecNumber(buffer, offset + 474, 1, "MW: Large sword"));
    list.add(new DecNumber(buffer, offset + 475, 1, "SW: Small blade"));
    list.add(new DecNumber(buffer, offset + 476, 1, "Toughness"));
    list.add(new DecNumber(buffer, offset + 477, 1, "Armored arcana"));
    list.add(new DecNumber(buffer, offset + 478, 1, "Cleave"));
    list.add(new DecNumber(buffer, offset + 479, 1, "Armor proficiency"));
    list.add(new DecNumber(buffer, offset + 480, 1, "SF: Enchantment"));
    list.add(new DecNumber(buffer, offset + 481, 1, "SF: Evocation"));
    list.add(new DecNumber(buffer, offset + 482, 1, "SF: Necromancy"));
    list.add(new DecNumber(buffer, offset + 483, 1, "SF: Transmutation"));
    list.add(new DecNumber(buffer, offset + 484, 1, "Spell penetration"));
    list.add(new DecNumber(buffer, offset + 485, 1, "Extra rage"));
    list.add(new DecNumber(buffer, offset + 486, 1, "Extra wild shape"));
    list.add(new DecNumber(buffer, offset + 487, 1, "Extra smiting"));
    list.add(new DecNumber(buffer, offset + 488, 1, "Extra turning"));
    list.add(new DecNumber(buffer, offset + 489, 1, "EW: Bastard sword"));
    list.add(new Unknown(buffer, offset + 490, 38));
    list.add(new DecNumber(buffer, offset + 528, 1, "Alchemy"));
    list.add(new DecNumber(buffer, offset + 529, 1, "Animal empathy"));
    list.add(new DecNumber(buffer, offset + 530, 1, "Bluff"));
    list.add(new DecNumber(buffer, offset + 531, 1, "Concentration"));
    list.add(new DecNumber(buffer, offset + 532, 1, "Diplomacy"));
    list.add(new DecNumber(buffer, offset + 533, 1, "Disable device"));
    list.add(new DecNumber(buffer, offset + 534, 1, "Hide"));
    list.add(new DecNumber(buffer, offset + 535, 1, "Intimidate"));
    list.add(new DecNumber(buffer, offset + 536, 1, "Knowledge (arcana)"));
    list.add(new DecNumber(buffer, offset + 537, 1, "Move silently"));
    list.add(new DecNumber(buffer, offset + 538, 1, "Open lock"));
    list.add(new DecNumber(buffer, offset + 539, 1, "Pick pocket"));
    list.add(new DecNumber(buffer, offset + 540, 1, "Search"));
    list.add(new DecNumber(buffer, offset + 541, 1, "Spellcraft"));
    list.add(new DecNumber(buffer, offset + 542, 1, "Use magic device"));
    list.add(new DecNumber(buffer, offset + 543, 1, "Wilderness lore"));
    list.add(new Unknown(buffer, offset + 544, 50));
    list.add(new DecNumber(buffer, offset + 594, 1, "Challenge rating"));
    list.add(new IdsBitmap(buffer, offset + 595, 1, "Favored enemy 1", "RACE.IDS"));
    list.add(new IdsBitmap(buffer, offset + 596, 1, "Favored enemy 2", "RACE.IDS"));
    list.add(new IdsBitmap(buffer, offset + 597, 1, "Favored enemy 3", "RACE.IDS"));
    list.add(new IdsBitmap(buffer, offset + 598, 1, "Favored enemy 4", "RACE.IDS"));
    list.add(new IdsBitmap(buffer, offset + 599, 1, "Favored enemy 5", "RACE.IDS"));
    list.add(new IdsBitmap(buffer, offset + 600, 1, "Favored enemy 6", "RACE.IDS"));
    list.add(new IdsBitmap(buffer, offset + 601, 1, "Favored enemy 7", "RACE.IDS"));
    list.add(new IdsBitmap(buffer, offset + 602, 1, "Favored enemy 8", "RACE.IDS"));
    list.add(new Bitmap(buffer, offset + 603, 1, "Subrace",
                        new String[]{"Pureblood", "Aamimar/Drow/Gold dwarf/Strongheart halfling/Deep gnome",
                                     "Tiefling/Wild elf/Gray dwarf/Ghostwise halfling"}));
    list.add(new Unknown(buffer, offset + 604, 1));
    list.add(new Bitmap(buffer, offset + 605, 1, "Sex", s_sex));
    list.add(new DecNumber(buffer, offset + 606, 1, "Strength"));
    list.add(new DecNumber(buffer, offset + 607, 1, "Intelligence"));
    list.add(new DecNumber(buffer, offset + 608, 1, "Wisdom"));
    list.add(new DecNumber(buffer, offset + 609, 1, "Dexterity"));
    list.add(new DecNumber(buffer, offset + 610, 1, "Constitution"));
    list.add(new DecNumber(buffer, offset + 611, 1, "Charisma"));
    list.add(new DecNumber(buffer, offset + 612, 1, "Morale"));
    list.add(new DecNumber(buffer, offset + 613, 1, "Morale break"));
    list.add(new DecNumber(buffer, offset + 614, 2, "Morale recovery"));
//    list.add(new Unknown(buffer, offset + 615, 1));
    list.add(new IdsBitmap(buffer, offset + 616, 4, "Kit", "KIT.IDS"));
    list.add(new ResourceRef(buffer, offset + 620, "Override script", "BCS"));
    list.add(new ResourceRef(buffer, offset + 628, "Special script 2", "BCS"));
    list.add(new ResourceRef(buffer, offset + 636, "Combat script", "BCS"));
    list.add(new ResourceRef(buffer, offset + 644, "Special script 3", "BCS"));
    list.add(new ResourceRef(buffer, offset + 652, "Movement script", "BCS"));
    list.add(new Bitmap(buffer, offset + 660, 1, "Default visibility", s_visible));
    list.add(new Bitmap(buffer, offset + 661, 1, "Set extra death variable?", s_noyes));
    list.add(new Bitmap(buffer, offset + 662, 1, "Increment kill count?", s_noyes));
    list.add(new Unknown(buffer, offset + 663, 1));
    list.add(new DecNumber(buffer, offset + 664, 2, "Internal specifier 1"));
    list.add(new DecNumber(buffer, offset + 666, 2, "Internal specifier 2"));
    list.add(new DecNumber(buffer, offset + 668, 2, "Internal specifier 3"));
    list.add(new DecNumber(buffer, offset + 670, 2, "Internal specifier 4"));
    list.add(new DecNumber(buffer, offset + 672, 2, "Internal specifier 5"));
    list.add(new TextString(buffer, offset + 674, 32, "Death variable (set)"));
    list.add(new TextString(buffer, offset + 706, 32, "Death variable (increment)"));
    list.add(new Unknown(buffer, offset + 738, 2));
    list.add(new DecNumber(buffer, offset + 740, 2, "Saved location: X"));
    list.add(new DecNumber(buffer, offset + 742, 2, "Saved location: Y"));
    list.add(new DecNumber(buffer, offset + 744, 2, "Saved orientation"));
    list.add(new Unknown(buffer, offset + 746, 146));
    list.add(new IdsBitmap(buffer, offset + 892, 1, "Allegiance", "EA.IDS"));
    list.add(new IdsBitmap(buffer, offset + 893, 1, "General", "GENERAL.IDS"));
    list.add(new IdsBitmap(buffer, offset + 894, 1, "Race", "RACE.IDS"));
    list.add(new IdsBitmap(buffer, offset + 895, 1, "Class", "CLASS.IDS"));
    list.add(new IdsBitmap(buffer, offset + 896, 1, "Specific", "SPECIFIC.IDS"));
    list.add(new IdsBitmap(buffer, offset + 897, 1, "Gender", "GENDER.IDS"));
    list.add(new IdsBitmap(buffer, offset + 898, 1, "Functional specifier 1", "OBJECT.IDS"));
    list.add(new IdsBitmap(buffer, offset + 899, 1, "Functional specifier 2", "OBJECT.IDS"));
    list.add(new IdsBitmap(buffer, offset + 900, 1, "Functional specifier 3", "OBJECT.IDS"));
    list.add(new IdsBitmap(buffer, offset + 901, 1, "Functional specifier 4", "OBJECT.IDS"));
    list.add(new IdsBitmap(buffer, offset + 902, 1, "Functional specifier 5", "OBJECT.IDS"));
    list.add(new IdsBitmap(buffer, offset + 903, 1, "Alignment", "ALIGNMNT.IDS"));
    list.add(new DecNumber(buffer, offset + 904, 2, "Global identifier"));
    list.add(new DecNumber(buffer, offset + 906, 2, "Local identifier"));
    list.add(new TextString(buffer, offset + 908, 32, "Script name"));
    list.add(new Unknown(buffer, offset + 940, 6));

    // Bard spells
    for (int i = 0; i < 9; i++) {
      SectionOffset s_off = new SectionOffset(buffer, offset + 946 + 4 * i,
                                              "Bard" + (i + 1) + " spells offset", null);
      DecNumber s_count = new DecNumber(buffer, offset + 1198 + 4 * i, 4, "Bard" + (i + 1) + " spells count");
      list.add(s_off);
      list.add(s_count);
      AbstractStruct s = new Iwd2Struct(this, buffer, getExtraOffset() + s_off.getValue(),
                                        s_count, "Bard" + (i + 1) + " spells", Iwd2Struct.TYPE_SPELL);
      list.add(s);
//      s_off.setStaticStruct(s);
    }

    // Cleric spells
    for (int i = 0; i < 9; i++) {
      SectionOffset s_off = new SectionOffset(buffer, offset + 982 + 4 * i,
                                              "Cleric" + (i + 1) + " spells offset", null);
      DecNumber s_count = new DecNumber(buffer, offset + 1234 + 4 * i, 4,
                                        "Cleric" + (i + 1) + " spells count");
      list.add(s_off);
      list.add(s_count);
      AbstractStruct s = new Iwd2Struct(this, buffer, getExtraOffset() + s_off.getValue(),
                                        s_count, "Cleric" + (i + 1) + " spells", Iwd2Struct.TYPE_SPELL);
      list.add(s);
//      s_off.setStaticStruct(s);
    }

    // Druid spells
    for (int i = 0; i < 9; i++) {
      SectionOffset s_off = new SectionOffset(buffer, offset + 1018 + 4 * i,
                                              "Druid" + (i + 1) + " spells offset", null);
      DecNumber s_count = new DecNumber(buffer, offset + 1270 + 4 * i, 4,
                                        "Druid" + (i + 1) + " spells count");
      list.add(s_off);
      list.add(s_count);
      AbstractStruct s = new Iwd2Struct(this, buffer, getExtraOffset() + s_off.getValue(),
                                        s_count, "Druid" + (i + 1) + " spells", Iwd2Struct.TYPE_SPELL);
      list.add(s);
//      s_off.setStaticStruct(s);
    }

    // Paladin spells
    for (int i = 0; i < 9; i++) {
      SectionOffset s_off = new SectionOffset(buffer, offset + 1054 + 4 * i,
                                              "Paladin" + (i + 1) + " spells offset", null);
      DecNumber s_count = new DecNumber(buffer, offset + 1306 + 4 * i, 4,
                                        "Paladin" + (i + 1) + " spells count");
      list.add(s_off);
      list.add(s_count);
      AbstractStruct s = new Iwd2Struct(this, buffer, getExtraOffset() + s_off.getValue(),
                                        s_count, "Paladin" + (i + 1) + " spells", Iwd2Struct.TYPE_SPELL);
      list.add(s);
//      s_off.setStaticStruct(s);
    }

    // Ranger spells
    for (int i = 0; i < 9; i++) {
      SectionOffset s_off = new SectionOffset(buffer, offset + 1090 + 4 * i,
                                              "Ranger" + (i + 1) + " spells offset", null);
      DecNumber s_count = new DecNumber(buffer, offset + 1342 + 4 * i, 4,
                                        "Ranger" + (i + 1) + " spells count");
      list.add(s_off);
      list.add(s_count);
      AbstractStruct s = new Iwd2Struct(this, buffer, getExtraOffset() + s_off.getValue(),
                                        s_count, "Ranger" + (i + 1) + " spells", Iwd2Struct.TYPE_SPELL);
      list.add(s);
//      s_off.setStaticStruct(s);
    }

    // Sorcerer spells
    for (int i = 0; i < 9; i++) {
      SectionOffset s_off = new SectionOffset(buffer, offset + 1126 + 4 * i,
                                              "Sorcerer" + (i + 1) + " spells offset", null);
      DecNumber s_count = new DecNumber(buffer, offset + 1378 + 4 * i, 4,
                                        "Sorcerer" + (i + 1) + " spells count");
      list.add(s_off);
      list.add(s_count);
      AbstractStruct s = new Iwd2Struct(this, buffer, getExtraOffset() + s_off.getValue(),
                                        s_count, "Sorcerer" + (i + 1) + " spells", Iwd2Struct.TYPE_SPELL);
      list.add(s);
//      s_off.setStaticStruct(s);
    }

    // Wizard spells
    for (int i = 0; i < 9; i++) {
      SectionOffset s_off = new SectionOffset(buffer, offset + 1162 + 4 * i,
                                              "Wizard" + (i + 1) + " spells offset", null);
      DecNumber s_count = new DecNumber(buffer, offset + 1414 + 4 * i, 4,
                                        "Wizard" + (i + 1) + " spells count");
      list.add(s_off);
      list.add(s_count);
      AbstractStruct s = new Iwd2Struct(this, buffer, getExtraOffset() + s_off.getValue(),
                                        s_count, "Wizard" + (i + 1) + " spells", Iwd2Struct.TYPE_SPELL);
      list.add(s);
//      s_off.setStaticStruct(s);
    }

    // Domain spells
    for (int i = 0; i < 9; i++) {
      SectionOffset s_off = new SectionOffset(buffer, offset + 1450 + 4 * i,
                                              "Domain" + (i + 1) + " spells offset", null);
      DecNumber s_count = new DecNumber(buffer, offset + 1486 + 4 * i, 4,
                                        "Domain" + (i + 1) + " spells count");
      list.add(s_off);
      list.add(s_count);
      AbstractStruct s = new Iwd2Struct(this, buffer, getExtraOffset() + s_off.getValue(),
                                        s_count, "Domain" + (i + 1) + " spells", Iwd2Struct.TYPE_SPELL);
      list.add(s);
//      s_off.setStaticStruct(s);
    }

    // Innate abilities
    SectionOffset inn_off = new SectionOffset(buffer, offset + 1522, "Abilities offset", null);
    DecNumber inn_num = new DecNumber(buffer, offset + 1526, 4, "Abilities count");
    list.add(inn_off);
    list.add(inn_num);
    AbstractStruct inn_str = new Iwd2Struct(this, buffer, getExtraOffset() + inn_off.getValue(),
                                            inn_num, "Abilities", Iwd2Struct.TYPE_ABILITY);
    list.add(inn_str);
//    inn_off.setStaticStruct(inn_str);

    // Songs
    SectionOffset song_off = new SectionOffset(buffer, offset + 1530, "Songs offset", null);
    DecNumber song_num = new DecNumber(buffer, offset + 1534, 4, "Songs count");
    list.add(song_off);
    list.add(song_num);
    AbstractStruct song_str = new Iwd2Struct(this, buffer, getExtraOffset() + song_off.getValue(),
                                             song_num, "Songs", Iwd2Struct.TYPE_SONG);
    list.add(song_str);
//    song_off.setStaticStruct(song_str);

    // Shapes
    SectionOffset shape_off = new SectionOffset(buffer, offset + 1538, "Shapes offset", null);
    DecNumber shape_num = new DecNumber(buffer, offset + 1542, 4, "Shapes count");
    list.add(shape_off);
    list.add(shape_num);
    AbstractStruct shape_str = new Iwd2Struct(this, buffer, getExtraOffset() + shape_off.getValue(),
                                              shape_num, "Shape", Iwd2Struct.TYPE_SHAPE);
    list.add(shape_str);
//    shape_off.setStaticStruct(shape_str);

    SectionOffset itemslots_offset = new SectionOffset(buffer, offset + 1546, "Item slots offset", null);
    list.add(itemslots_offset);
    SectionOffset items_offset = new SectionOffset(buffer, offset + 1550, "Items offset",
                                                   Item.class);
    list.add(items_offset);
    SectionCount items_count = new SectionCount(buffer, offset + 1554, 4, "# items",
                                                Item.class);
    list.add(items_count);

    SectionOffset effects_offset;
    SectionCount effects_count;
    if (effect_flag.getValue() == 1) {
      effects_offset = new SectionOffset(buffer, offset + 1558, "Effects offset",
                                         Effect2.class);
      effects_count = new SectionCount(buffer, offset + 1562, 4, "# effects",
                                       Effect2.class);
    }
    else {
      effects_offset = new SectionOffset(buffer, offset + 1558, "Effects offset",
                                         Effect.class);
      effects_count = new SectionCount(buffer, offset + 1562, 4, "# effects",
                                       Effect.class);
    }
    list.add(effects_offset);
    list.add(effects_count);
    list.add(new ResourceRef(buffer, offset + 1566, "Dialogue", "DLG"));

    offset = getExtraOffset() + effects_offset.getValue();
    if (effect_flag.getValue() == 1)
      for (int i = 0; i < effects_count.getValue(); i++) {
        Effect2 eff = new Effect2(this, buffer, offset);
        offset = eff.getEndOffset();
        list.add(eff);
      }
    else
      for (int i = 0; i < effects_count.getValue(); i++) {
        Effect eff = new Effect(this, buffer, offset);
        offset = eff.getEndOffset();
        list.add(eff);
      }

    offset = getExtraOffset() + items_offset.getValue();
    for (int i = 0; i < items_count.getValue(); i++) {
      Item item = new Item(this, buffer, offset, i);
      offset = item.getEndOffset();
      list.add(item);
    }

    offset = getExtraOffset() + itemslots_offset.getValue();
    list.add(new DecNumber(buffer, offset, 2, "Helmet"));
    list.add(new DecNumber(buffer, offset + 2, 2, "Armor"));
    list.add(new DecNumber(buffer, offset + 4, 2, "Shield"));
    list.add(new DecNumber(buffer, offset + 6, 2, "Gauntlets"));
    list.add(new DecNumber(buffer, offset + 8, 2, "Left ring"));
    list.add(new DecNumber(buffer, offset + 10, 2, "Right ring"));
    list.add(new DecNumber(buffer, offset + 12, 2, "Amulet"));
    list.add(new DecNumber(buffer, offset + 14, 2, "Belt"));
    list.add(new DecNumber(buffer, offset + 16, 2, "Boots"));
    list.add(new DecNumber(buffer, offset + 18, 2, "Weapon 1"));
    list.add(new DecNumber(buffer, offset + 20, 2, "Shield 1"));
    list.add(new DecNumber(buffer, offset + 22, 2, "Weapon 2"));
    list.add(new DecNumber(buffer, offset + 24, 2, "Shield 2"));
    list.add(new DecNumber(buffer, offset + 26, 2, "Weapon 3"));
    list.add(new DecNumber(buffer, offset + 28, 2, "Shield 3"));
    list.add(new DecNumber(buffer, offset + 30, 2, "Weapon 4"));
    list.add(new DecNumber(buffer, offset + 32, 2, "Shield 4"));
    list.add(new DecNumber(buffer, offset + 34, 2, "Cloak"));
    list.add(new DecNumber(buffer, offset + 36, 2, "Quiver 1"));
    list.add(new DecNumber(buffer, offset + 38, 2, "Quiver 2"));
    list.add(new DecNumber(buffer, offset + 40, 2, "Quiver 3"));
    list.add(new DecNumber(buffer, offset + 42, 2, "Quiver 4"));
    list.add(new DecNumber(buffer, offset + 44, 2, "Quick item 1"));
    list.add(new DecNumber(buffer, offset + 46, 2, "Quick item 2"));
    list.add(new DecNumber(buffer, offset + 48, 2, "Quick item 3"));
    list.add(new DecNumber(buffer, offset + 50, 2, "Inventory 1"));
    list.add(new DecNumber(buffer, offset + 52, 2, "Inventory 2"));
    list.add(new DecNumber(buffer, offset + 54, 2, "Inventory 3"));
    list.add(new DecNumber(buffer, offset + 56, 2, "Inventory 4"));
    list.add(new DecNumber(buffer, offset + 58, 2, "Inventory 5"));
    list.add(new DecNumber(buffer, offset + 60, 2, "Inventory 6"));
    list.add(new DecNumber(buffer, offset + 62, 2, "Inventory 7"));
    list.add(new DecNumber(buffer, offset + 64, 2, "Inventory 8"));
    list.add(new DecNumber(buffer, offset + 66, 2, "Inventory 9"));
    list.add(new DecNumber(buffer, offset + 68, 2, "Inventory 10"));
    list.add(new DecNumber(buffer, offset + 70, 2, "Inventory 11"));
    list.add(new DecNumber(buffer, offset + 72, 2, "Inventory 12"));
    list.add(new DecNumber(buffer, offset + 74, 2, "Inventory 13"));
    list.add(new DecNumber(buffer, offset + 76, 2, "Inventory 14"));
    list.add(new DecNumber(buffer, offset + 78, 2, "Inventory 15"));
    list.add(new DecNumber(buffer, offset + 80, 2, "Inventory 16"));
    list.add(new DecNumber(buffer, offset + 82, 2, "Inventory 17"));
    list.add(new DecNumber(buffer, offset + 84, 2, "Inventory 18"));
    list.add(new DecNumber(buffer, offset + 86, 2, "Inventory 19"));
    list.add(new DecNumber(buffer, offset + 88, 2, "Inventory 20"));
    list.add(new DecNumber(buffer, offset + 90, 2, "Inventory 21"));
    list.add(new DecNumber(buffer, offset + 92, 2, "Inventory 22"));
    list.add(new DecNumber(buffer, offset + 94, 2, "Inventory 23"));
    list.add(new DecNumber(buffer, offset + 96, 2, "Inventory 24"));
    list.add(new DecNumber(buffer, offset + 98, 2, "Magically created weapon"));
    list.add(new DecNumber(buffer, offset + 100, 2, "Weapon slot selected"));
    list.add(new Unknown(buffer, offset + 102, 2));

    int endoffset = offset;
    for (int i = 0; i < list.size(); i++) {
      StructEntry entry = list.get(i);
      if (entry.getOffset() + entry.getSize() > endoffset)
        endoffset = entry.getOffset() + entry.getSize();
    }
    return endoffset;
  }

  ////////////////////////
  // Other IE games
  ////////////////////////

  private int readOther(String version, byte buffer[], int offset) throws Exception
  {
    list.add(new StringRef(buffer, offset, "Name"));
    list.add(new StringRef(buffer, offset + 4, "Tooltip"));
    list.add(new Flag(buffer, offset + 8, 4, "Flags", s_flag));
    list.add(new DecNumber(buffer, offset + 12, 4, "XP value"));
    list.add(new DecNumber(buffer, offset + 16, 4, "XP"));
    list.add(new DecNumber(buffer, offset + 20, 4, "Gold"));
    list.add(new IdsFlag(buffer, offset + 24, 4, "Status", "STATE.IDS"));
    list.add(new DecNumber(buffer, offset + 28, 2, "Current HP"));
    list.add(new DecNumber(buffer, offset + 30, 2, "Maximum HP"));
    list.add(new IdsBitmap(buffer, offset + 32, 4, "Animation", "ANIMATE.IDS"));
//    list.add(new Unknown(buffer, offset + 34, 2));
//    if (version.equalsIgnoreCase("V1.2") || version.equalsIgnoreCase("V1.1"))
//      list.add(new Unknown(buffer, offset + 36, 7));
//    else {
      list.add(new ColorValue(buffer, offset + 36, 1, "Metal color"));
      list.add(new ColorValue(buffer, offset + 37, 1, "Minor color"));
      list.add(new ColorValue(buffer, offset + 38, 1, "Major color"));
      list.add(new ColorValue(buffer, offset + 39, 1, "Skin color"));
      list.add(new ColorValue(buffer, offset + 40, 1, "Leather color"));
      list.add(new ColorValue(buffer, offset + 41, 1, "Armor color"));
      list.add(new ColorValue(buffer, offset + 42, 1, "Hair color"));
//    }
    DecNumber effect_flag = new DecNumber(buffer, offset + 43, 1, "Effect flag");
    list.add(effect_flag);
    list.add(new ResourceRef(buffer, offset + 44, "Small portrait", "BMP"));
    if (version.equalsIgnoreCase("V1.2") || version.equalsIgnoreCase("V1.1"))
      list.add(new ResourceRef(buffer, offset + 52, "Large portrait", "BAM"));
    else
      list.add(new ResourceRef(buffer, offset + 52, "Large portrait", "BMP"));
    list.add(new UnsignDecNumber(buffer, offset + 60, 1, "Reputation"));
    list.add(new UnsignDecNumber(buffer, offset + 61, 1, "Hide in shadows"));
    list.add(new DecNumber(buffer, offset + 62, 2, "Natural AC"));
    list.add(new DecNumber(buffer, offset + 64, 2, "Effective AC"));
    list.add(new DecNumber(buffer, offset + 66, 2, "Crushing AC modifier"));
    list.add(new DecNumber(buffer, offset + 68, 2, "Missile AC modifier"));
    list.add(new DecNumber(buffer, offset + 70, 2, "Piercing AC modifier"));
    list.add(new DecNumber(buffer, offset + 72, 2, "Slashing AC modifier"));
    list.add(new DecNumber(buffer, offset + 74, 1, "THAC0"));
//    if (version.equalsIgnoreCase("V1.2") || version.equalsIgnoreCase("V1.1"))
      list.add(new Bitmap(buffer, offset + 75, 1, "# attacks", s_attacks));
//    else
//      list.add(new DecNumber(buffer, offset + 75, 1, "# attacks"));
    list.add(new DecNumber(buffer, offset + 76, 1, "Save vs. death"));
    list.add(new DecNumber(buffer, offset + 77, 1, "Save vs. wand"));
    list.add(new DecNumber(buffer, offset + 78, 1, "Save vs. polymorph"));
    list.add(new DecNumber(buffer, offset + 79, 1, "Save vs. breath"));
    list.add(new DecNumber(buffer, offset + 80, 1, "Save vs. spell"));
    list.add(new DecNumber(buffer, offset + 81, 1, "Resist fire"));
    list.add(new DecNumber(buffer, offset + 82, 1, "Resist cold"));
    list.add(new DecNumber(buffer, offset + 83, 1, "Resist electricity"));
    list.add(new DecNumber(buffer, offset + 84, 1, "Resist acid"));
    list.add(new DecNumber(buffer, offset + 85, 1, "Resist magic"));
    list.add(new DecNumber(buffer, offset + 86, 1, "Resist magic fire"));
    list.add(new DecNumber(buffer, offset + 87, 1, "Resist magic cold"));
    list.add(new DecNumber(buffer, offset + 88, 1, "Resist slashing"));
    list.add(new DecNumber(buffer, offset + 89, 1, "Resist crushing"));
    list.add(new DecNumber(buffer, offset + 90, 1, "Resist piercing"));
    list.add(new DecNumber(buffer, offset + 91, 1, "Resist missile"));
//    if (version.equalsIgnoreCase("V1.2") || version.equalsIgnoreCase("V1.1")) {
//      list.add(new DecNumber(buffer, offset + 92, 1, "Unused proficiencies"));
//      list.add(new Unknown(buffer, offset + 93, 1));
//    }
//    else {
      list.add(new UnsignDecNumber(buffer, offset + 92, 1, "Detect illusions"));
      list.add(new UnsignDecNumber(buffer, offset + 93, 1, "Set traps"));
//    }
    list.add(new DecNumber(buffer, offset + 94, 1, "Lore"));
    list.add(new UnsignDecNumber(buffer, offset + 95, 1, "Open locks"));
    list.add(new UnsignDecNumber(buffer, offset + 96, 1, "Move silently"));
    list.add(new UnsignDecNumber(buffer, offset + 97, 1, "Find traps"));
    list.add(new UnsignDecNumber(buffer, offset + 98, 1, "Pick pockets"));
    list.add(new DecNumber(buffer, offset + 99, 1, "Fatigue"));
    list.add(new DecNumber(buffer, offset + 100, 1, "Intoxication"));
    list.add(new DecNumber(buffer, offset + 101, 1, "Luck"));
    if (version.equals("V1.0")) {
      list.add(new DecNumber(buffer, offset + 102, 1, "Large sword proficiency"));
      list.add(new DecNumber(buffer, offset + 103, 1, "Small sword proficiency"));
      list.add(new DecNumber(buffer, offset + 104, 1, "Bow proficiency"));
      list.add(new DecNumber(buffer, offset + 105, 1, "Spear proficiency"));
      list.add(new DecNumber(buffer, offset + 106, 1, "Blunt proficiency"));
      list.add(new DecNumber(buffer, offset + 107, 1, "Spiked proficiency"));
      list.add(new DecNumber(buffer, offset + 108, 1, "Axe proficiency"));
      list.add(new DecNumber(buffer, offset + 109, 1, "Missile proficiency"));
      list.add(new Unknown(buffer, offset + 110, 13));
    }
    else if (version.equalsIgnoreCase("V1.2") || version.equalsIgnoreCase("V1.1")) {
      list.add(new DecNumber(buffer, offset + 102, 1, "Fist proficiency"));
      list.add(new DecNumber(buffer, offset + 103, 1, "Edged-weapon proficiency"));
      list.add(new DecNumber(buffer, offset + 104, 1, "Hammer proficiency"));
      list.add(new DecNumber(buffer, offset + 105, 1, "Axe proficiency"));
      list.add(new DecNumber(buffer, offset + 106, 1, "Club proficiency"));
      list.add(new DecNumber(buffer, offset + 107, 1, "Bow proficiency"));
//      list.add(new DecNumber(buffer, offset + 108, 1, "Extra proficiency 1"));
//      list.add(new DecNumber(buffer, offset + 109, 1, "Extra proficiency 2"));
//      list.add(new DecNumber(buffer, offset + 110, 1, "Extra proficiency 3"));
//      list.add(new DecNumber(buffer, offset + 111, 1, "Extra proficiency 4"));
//      list.add(new DecNumber(buffer, offset + 112, 1, "Extra proficiency 5"));
//      list.add(new DecNumber(buffer, offset + 113, 1, "Extra proficiency 6"));
//      list.add(new DecNumber(buffer, offset + 114, 1, "Extra proficiency 7"));
//      list.add(new DecNumber(buffer, offset + 115, 1, "Extra proficiency 8"));
//      list.add(new DecNumber(buffer, offset + 116, 1, "Extra proficiency 9"));
      list.add(new Unknown(buffer, offset + 108, 15));
    }
    else if (version.equalsIgnoreCase("V9.0")) {
      list.add(new DecNumber(buffer, offset + 102, 1, "Large sword proficiency"));
      list.add(new DecNumber(buffer, offset + 103, 1, "Small sword proficiency"));
      list.add(new DecNumber(buffer, offset + 104, 1, "Bow proficiency"));
      list.add(new DecNumber(buffer, offset + 105, 1, "Spear proficiency"));
      list.add(new DecNumber(buffer, offset + 106, 1, "Axe proficiency"));
      list.add(new DecNumber(buffer, offset + 107, 1, "Missile proficiency"));
      list.add(new DecNumber(buffer, offset + 108, 1, "Greatsword proficiency"));
      list.add(new DecNumber(buffer, offset + 109, 1, "Dagger proficiency"));
      list.add(new DecNumber(buffer, offset + 110, 1, "Halberd proficiency"));
      list.add(new DecNumber(buffer, offset + 111, 1, "Mace proficiency"));
      list.add(new DecNumber(buffer, offset + 112, 1, "Flail proficiency"));
      list.add(new DecNumber(buffer, offset + 113, 1, "Hammer proficiency"));
      list.add(new DecNumber(buffer, offset + 114, 1, "Club proficiency"));
      list.add(new DecNumber(buffer, offset + 115, 1, "Quarterstaff proficiency"));
      list.add(new DecNumber(buffer, offset + 116, 1, "Crossbow proficiency"));
      list.add(new Unknown(buffer, offset + 117, 6));
    }
    else {
      list.clear();
      throw new Exception("Unsupported version: " + version);
    }
    list.add(new DecNumber(buffer, offset + 123, 1, "Tracking"));
    list.add(new Unknown(buffer, offset + 124, 32));
    LongIntegerHashMap sndmap = null;
    if (ResourceFactory.getInstance().resourceExists("SNDSLOT.IDS"))
      sndmap = IdsMapCache.get("SNDSLOT.IDS").getMap();
    else if (ResourceFactory.getInstance().resourceExists("SOUNDOFF.IDS"))
      sndmap = IdsMapCache.get("SOUNDOFF.IDS").getMap();
    if (sndmap != null) {
      for (int i = 0; i < 100; i++)
        if (sndmap.containsKey((long)i))
          list.add(
                  new StringRef(buffer, offset + 156 + i * 4,
                                "Sound: " + ((IdsMapEntry)sndmap.get((long)i)).getString()));
        else
          list.add(new StringRef(buffer, offset + 156 + i * 4, "Sound: Unknown"));
    }
    else {
      for (int i = 0; i < 100; i++)
        list.add(new StringRef(buffer, offset + 156 + i * 4, "Soundset string"));
    }
    list.add(new DecNumber(buffer, offset + 556, 1, "Level first class"));
    list.add(new DecNumber(buffer, offset + 557, 1, "Level second class"));
    list.add(new DecNumber(buffer, offset + 558, 1, "Level third class"));
    list.add(
            new Bitmap(buffer, offset + 559, 1, "Sex", s_sex));
    list.add(new DecNumber(buffer, offset + 560, 1, "Strength"));
    list.add(new DecNumber(buffer, offset + 561, 1, "Strength bonus"));
    list.add(new DecNumber(buffer, offset + 562, 1, "Intelligence"));
    list.add(new DecNumber(buffer, offset + 563, 1, "Wisdom"));
    list.add(new DecNumber(buffer, offset + 564, 1, "Dexterity"));
    list.add(new DecNumber(buffer, offset + 565, 1, "Constitution"));
    list.add(new DecNumber(buffer, offset + 566, 1, "Charisma"));
    list.add(new DecNumber(buffer, offset + 567, 1, "Morale"));
    list.add(new DecNumber(buffer, offset + 568, 1, "Morale break"));
    list.add(new IdsBitmap(buffer, offset + 569, 1, "Racial enemy", "RACE.IDS"));
    list.add(new DecNumber(buffer, offset + 570, 2, "Morale recovery"));
//    list.add(new Unknown(buffer, offset + 571, 1));
    if (ResourceFactory.getInstance().resourceExists("KITLIST.2DA"))
      list.add(new Kit2daBitmap(buffer, offset + 572));
    else {
      if (ResourceFactory.getInstance().resourceExists("DEITY.IDS"))
        list.add(new IdsBitmap(buffer, offset + 572, 2, "Deity", "DEITY.IDS"));
      else if (ResourceFactory.getInstance().resourceExists("DIETY.IDS"))
        list.add(new IdsBitmap(buffer, offset + 572, 2, "Deity", "DIETY.IDS"));
      else
        list.add(new Unknown(buffer, offset + 572, 2));
      if (ResourceFactory.getInstance().resourceExists("MAGESPEC.IDS"))
        list.add(new IdsBitmap(buffer, offset + 574, 2, "Mage type", "MAGESPEC.IDS"));
      else
        list.add(new HashBitmap(buffer, offset + 574, 2, "Mage type", m_magetype));
    }
    list.add(new ResourceRef(buffer, offset + 576, "Override script", "BCS"));
    list.add(new ResourceRef(buffer, offset + 584, "Class script", "BCS"));
    list.add(new ResourceRef(buffer, offset + 592, "Race script", "BCS"));
    list.add(new ResourceRef(buffer, offset + 600, "General script", "BCS"));
    list.add(new ResourceRef(buffer, offset + 608, "Default script", "BCS"));
    if (version.equalsIgnoreCase("V1.2") || version.equalsIgnoreCase("V1.1")) {
//      LongIntegerHashMap<String> m_zoom = new LongIntegerHashMap<String>();
//      m_zoom.put(0x0000L, "No");
//      m_zoom.put(0xffffL, "Yes");
      list.add(new Unknown(buffer, offset + 616, 24));
      list.add(new HashBitmap(buffer, offset + 640, 4, "Is disguised?", m_zoom));
      list.add(new Unknown(buffer, offset + 644, 8));
      list.add(new Unknown(buffer, offset + 652, 4, "Overlays offset"));
      list.add(new Unknown(buffer, offset + 656, 4, "Overlays size"));
      list.add(new DecNumber(buffer, offset + 660, 4, "XP second class"));
      list.add(new DecNumber(buffer, offset + 664, 4, "XP third class"));
      LongIntegerHashMap intMap = IdsMapCache.get("INTERNAL.IDS").getMap();
      for (int i = 0; i < 10; i++) {
        if (intMap.containsKey((long)i))
          list.add(
                  new DecNumber(buffer, offset + 668 + i * 2, 2,
                                ((IdsMapEntry)intMap.get((long)i)).getString()));
        else
          list.add(new DecNumber(buffer, offset + 668 + i * 2, 2, "Internal " + i));
      }
      list.add(new DecNumber(buffer, offset + 688, 1, "Good increment by"));
      list.add(new DecNumber(buffer, offset + 689, 1, "Law increment by"));
      list.add(new DecNumber(buffer, offset + 690, 1, "Lady increment by"));
      list.add(new DecNumber(buffer, offset + 691, 1, "Murder increment by"));
      list.add(new TextString(buffer, offset + 692, 32, "Character type"));
      list.add(new DecNumber(buffer, offset + 724, 1, "Dialogue activation radius"));
      list.add(new DecNumber(buffer, offset + 725, 1, "Collision radius")); // 0x2dd
      list.add(new Unknown(buffer, offset + 726, 1));
      list.add(new DecNumber(buffer, offset + 727, 1, "# colors"));
      list.add(new Flag(buffer, offset + 728, 4, "Attributes",
                        new String[]{"No flags set", "", "Transparent", "", "",
                                     "Increment death variable", "Increment kill count",
                                     "Script name only", "Increment faction kills",
                                     "Increment team kills", "Invulnerable",
                                     "Good increment on death", "Law increment on death",
                                     "Lady increment on death", "Murder increment on death",
                                     "Face dialogue target", "Call for help"}));
//      list.add(new Unknown(buffer, offset + 730, 2));
      list.add(new IdsBitmap(buffer, offset + 732, 2, "Color 1", "CLOWNCLR.IDS"));
      list.add(new IdsBitmap(buffer, offset + 734, 2, "Color 2", "CLOWNCLR.IDS"));
      list.add(new IdsBitmap(buffer, offset + 736, 2, "Color 3", "CLOWNCLR.IDS"));
      list.add(new IdsBitmap(buffer, offset + 738, 2, "Color 4", "CLOWNCLR.IDS"));
      list.add(new IdsBitmap(buffer, offset + 740, 2, "Color 5", "CLOWNCLR.IDS"));
      list.add(new IdsBitmap(buffer, offset + 742, 2, "Color 6", "CLOWNCLR.IDS"));
      list.add(new IdsBitmap(buffer, offset + 744, 2, "Color 7", "CLOWNCLR.IDS"));
      list.add(new Unknown(buffer, offset + 746, 3));
      list.add(new HashBitmap(buffer, offset + 749, 1, "Color 1 placement", m_colorPlacement));
      list.add(new HashBitmap(buffer, offset + 750, 1, "Color 2 placement", m_colorPlacement));
      list.add(new HashBitmap(buffer, offset + 751, 1, "Color 3 placement", m_colorPlacement));
      list.add(new HashBitmap(buffer, offset + 752, 1, "Color 4 placement", m_colorPlacement));
      list.add(new HashBitmap(buffer, offset + 753, 1, "Color 5 placement", m_colorPlacement));
      list.add(new HashBitmap(buffer, offset + 754, 1, "Color 6 placement", m_colorPlacement));
      list.add(new HashBitmap(buffer, offset + 755, 1, "Color 7 placement", m_colorPlacement));
      list.add(new Unknown(buffer, offset + 756, 21));
      list.add(new IdsBitmap(buffer, offset + 777, 1, "Species", "RACE.IDS"));
      list.add(new IdsBitmap(buffer, offset + 778, 1, "Team", "TEAM.IDS"));
      list.add(new IdsBitmap(buffer, offset + 779, 1, "Faction", "FACTION.IDS"));
      offset += 164;
    }
    else if (version.equalsIgnoreCase("V9.0")) {
      list.add(new Bitmap(buffer, offset + 616, 1, "Default visibility", s_visible));
      list.add(new Bitmap(buffer, offset + 617, 1, "Set extra death variable?", s_noyes));
      list.add(new Bitmap(buffer, offset + 618, 1, "Increment kill count?", s_noyes));
      list.add(new Unknown(buffer, offset + 619, 1));
      list.add(new DecNumber(buffer, offset + 620, 2, "Internal specifier 1"));
      list.add(new DecNumber(buffer, offset + 622, 2, "Internal specifier 2"));
      list.add(new DecNumber(buffer, offset + 624, 2, "Internal specifier 3"));
      list.add(new DecNumber(buffer, offset + 626, 2, "Internal specifier 4"));
      list.add(new DecNumber(buffer, offset + 628, 2, "Internal specifier 5"));
      list.add(new TextString(buffer, offset + 630, 32, "Death variable (set)"));
      list.add(new TextString(buffer, offset + 662, 32, "Death variable (increment)"));
      list.add(new Unknown(buffer, offset + 694, 2));
      list.add(new DecNumber(buffer, offset + 696, 2, "Saved location: X"));
      list.add(new DecNumber(buffer, offset + 698, 2, "Saved location: Y"));
      list.add(new DecNumber(buffer, offset + 700, 2, "Saved orientation"));
      list.add(new Unknown(buffer, offset + 702, 18));
      offset += 104;
    }
    list.add(new IdsBitmap(buffer, offset + 616, 1, "Allegiance", "EA.IDS"));
    list.add(new IdsBitmap(buffer, offset + 617, 1, "General", "GENERAL.IDS"));
    list.add(new IdsBitmap(buffer, offset + 618, 1, "Race", "RACE.IDS"));
    list.add(new IdsBitmap(buffer, offset + 619, 1, "Class", "CLASS.IDS"));
    list.add(new IdsBitmap(buffer, offset + 620, 1, "Specific", "SPECIFIC.IDS"));
    list.add(new IdsBitmap(buffer, offset + 621, 1, "Gender", "GENDER.IDS"));
    list.add(new IdsBitmap(buffer, offset + 622, 1, "Functional specifier 1", "OBJECT.IDS"));
    list.add(new IdsBitmap(buffer, offset + 623, 1, "Functional specifier 2", "OBJECT.IDS"));
    list.add(new IdsBitmap(buffer, offset + 624, 1, "Functional specifier 3", "OBJECT.IDS"));
    list.add(new IdsBitmap(buffer, offset + 625, 1, "Functional specifier 4", "OBJECT.IDS"));
    list.add(new IdsBitmap(buffer, offset + 626, 1, "Functional specifier 5", "OBJECT.IDS"));
//    if (ResourceFactory.getGameID() == ResourceFactory.ID_BG2 ||
//        ResourceFactory.getGameID() == ResourceFactory.ID_BG2TOB)
//      list.add(new IdsBitmap(buffer, offset + 627, 1, "Alignment", "ALIGN.IDS"));
//    else
      list.add(new IdsBitmap(buffer, offset + 627, 1, "Alignment", "ALIGNMEN.IDS"));
    list.add(new DecNumber(buffer, offset + 628, 2, "Global identifier"));
    list.add(new DecNumber(buffer, offset + 630, 2, "Local identifier"));
    list.add(new TextString(buffer, offset + 632, 32, "Script name"));
//    list.add(new Unknown(buffer, offset + 650, 14));

    SectionOffset offsetKnownSpells = new SectionOffset(buffer, offset + 664, "Known spells offset",
                                                        KnownSpells.class);
    list.add(offsetKnownSpells);
    SectionCount countKnownSpells = new SectionCount(buffer, offset + 668, 4, "# known spells",
                                                     KnownSpells.class);
    list.add(countKnownSpells);
    SectionOffset offsetMemSpellInfo = new SectionOffset(buffer, offset + 672, "Memorization info offset",
                                                         SpellMemorization.class);
    list.add(offsetMemSpellInfo);
    SectionCount countMemSpellInfo = new SectionCount(buffer, offset + 676, 4, "# memorization info",
                                                      SpellMemorization.class);
    list.add(countMemSpellInfo);
    SectionOffset offsetMemSpells = new SectionOffset(buffer, offset + 680, "Memorized spells offset",
                                                      MemorizedSpells.class);
    list.add(offsetMemSpells);
    SectionCount countMemSpells = new SectionCount(buffer, offset + 684, 4, "# memorized spells",
                                                   MemorizedSpells.class);
    list.add(countMemSpells);
    SectionOffset offsetItemslots = new SectionOffset(buffer, offset + 688, "Item slots offset", null);
    list.add(offsetItemslots);
    SectionOffset offsetItems = new SectionOffset(buffer, offset + 692, "Items offset", Item.class);
    list.add(offsetItems);
    SectionCount countItems = new SectionCount(buffer, offset + 696, 4, "# items", Item.class);
    list.add(countItems);
    SectionOffset offsetEffects;
    SectionCount countEffects;
    if (effect_flag.getValue() == 1) {
      offsetEffects = new SectionOffset(buffer, offset + 700, "Effects offset", Effect2.class);
      countEffects = new SectionCount(buffer, offset + 704, 4, "# effects", Effect2.class);
    }
    else {
      offsetEffects = new SectionOffset(buffer, offset + 700, "Effects offset", Effect.class);
      countEffects = new SectionCount(buffer, offset + 704, 4, "# effects", Effect.class);
    }
    list.add(offsetEffects);
    list.add(countEffects);
    list.add(new ResourceRef(buffer, offset + 708, "Dialogue", "DLG"));

    offset = getExtraOffset() + offsetKnownSpells.getValue();
    for (int i = 0; i < countKnownSpells.getValue(); i++) {
      KnownSpells known = new KnownSpells(this, buffer, offset);
      offset = known.getEndOffset();
      list.add(known);
    }

    offset = getExtraOffset() + offsetMemSpellInfo.getValue();
    for (int i = 0; i < countMemSpellInfo.getValue(); i++) {
      SpellMemorization mem = new SpellMemorization(this, buffer, offset, i);
      offset = mem.getEndOffset();
      mem.readMemorizedSpells(buffer, offsetMemSpells.getValue() + getExtraOffset());
      list.add(mem);
    }

    offset = getExtraOffset() + offsetEffects.getValue();
    if (effect_flag.getValue() == 1)
      for (int i = 0; i < countEffects.getValue(); i++) {
        Effect2 eff = new Effect2(this, buffer, offset);
        offset = eff.getEndOffset();
        list.add(eff);
      }
    else
      for (int i = 0; i < countEffects.getValue(); i++) {
        Effect eff = new Effect(this, buffer, offset);
        offset = eff.getEndOffset();
        list.add(eff);
      }

    offset = getExtraOffset() + offsetItems.getValue();
    for (int i = 0; i < countItems.getValue(); i++) {
      Item item = new Item(this, buffer, offset, i);
      offset = item.getEndOffset();
      list.add(item);
    }

    offset = getExtraOffset() + offsetItemslots.getValue();
    if (version.equalsIgnoreCase("V1.2")) {
      list.add(new DecNumber(buffer, offset, 2, "Right earring/lens"));
      list.add(new DecNumber(buffer, offset + 2, 2, "Armor"));
      list.add(new DecNumber(buffer, offset + 4, 2, "Left tattoo"));
      list.add(new DecNumber(buffer, offset + 6, 2, "Hand"));
      list.add(new DecNumber(buffer, offset + 8, 2, "Left ring"));
      list.add(new DecNumber(buffer, offset + 10, 2, "Right ring"));
      list.add(new DecNumber(buffer, offset + 12, 2, "Left earring/eyeball"));
      list.add(new DecNumber(buffer, offset + 14, 2, "Right tattoo (lower)"));
      list.add(new DecNumber(buffer, offset + 16, 2, "Wrist"));
      list.add(new DecNumber(buffer, offset + 18, 2, "Weapon 1"));
      list.add(new DecNumber(buffer, offset + 20, 2, "Weapon 2"));
      list.add(new DecNumber(buffer, offset + 22, 2, "Weapon 3"));
      list.add(new DecNumber(buffer, offset + 24, 2, "Weapon 4"));
      list.add(new DecNumber(buffer, offset + 26, 2, "Quiver 1"));
      list.add(new DecNumber(buffer, offset + 28, 2, "Quiver 2"));
      list.add(new DecNumber(buffer, offset + 30, 2, "Quiver 3"));
      list.add(new DecNumber(buffer, offset + 32, 2, "Quiver 4"));
      list.add(new DecNumber(buffer, offset + 34, 2, "Quiver 5"));
      list.add(new DecNumber(buffer, offset + 36, 2, "Quiver 6"));
      list.add(new DecNumber(buffer, offset + 38, 2, "Right tattoo (upper)"));
      list.add(new DecNumber(buffer, offset + 40, 2, "Quick item 1"));
      list.add(new DecNumber(buffer, offset + 42, 2, "Quick item 2"));
      list.add(new DecNumber(buffer, offset + 44, 2, "Quick item 3"));
      list.add(new DecNumber(buffer, offset + 46, 2, "Quick item 4"));
      list.add(new DecNumber(buffer, offset + 48, 2, "Quick item 5"));
      list.add(new DecNumber(buffer, offset + 50, 2, "Inventory 1"));
      list.add(new DecNumber(buffer, offset + 52, 2, "Inventory 2"));
      list.add(new DecNumber(buffer, offset + 54, 2, "Inventory 3"));
      list.add(new DecNumber(buffer, offset + 56, 2, "Inventory 4"));
      list.add(new DecNumber(buffer, offset + 58, 2, "Inventory 5"));
      list.add(new DecNumber(buffer, offset + 60, 2, "Inventory 6"));
      list.add(new DecNumber(buffer, offset + 62, 2, "Inventory 7"));
      list.add(new DecNumber(buffer, offset + 64, 2, "Inventory 8"));
      list.add(new DecNumber(buffer, offset + 66, 2, "Inventory 9"));
      list.add(new DecNumber(buffer, offset + 68, 2, "Inventory 10"));
      list.add(new DecNumber(buffer, offset + 70, 2, "Inventory 11"));
      list.add(new DecNumber(buffer, offset + 72, 2, "Inventory 12"));
      list.add(new DecNumber(buffer, offset + 74, 2, "Inventory 13"));
      list.add(new DecNumber(buffer, offset + 76, 2, "Inventory 14"));
      list.add(new DecNumber(buffer, offset + 78, 2, "Inventory 15"));
      list.add(new DecNumber(buffer, offset + 80, 2, "Inventory 16"));
      list.add(new DecNumber(buffer, offset + 82, 2, "Inventory 17"));
      list.add(new DecNumber(buffer, offset + 84, 2, "Inventory 18"));
      list.add(new DecNumber(buffer, offset + 86, 2, "Inventory 19"));
      list.add(new DecNumber(buffer, offset + 88, 2, "Inventory 20"));
      list.add(new DecNumber(buffer, offset + 90, 2, "Magically created weapon"));
      list.add(new DecNumber(buffer, offset + 92, 2, "Weapon slot selected"));
      list.add(new DecNumber(buffer, offset + 94, 2, "Weapon ability selected"));
    }
    else {
      list.add(new DecNumber(buffer, offset, 2, "Helmet"));
      list.add(new DecNumber(buffer, offset + 2, 2, "Armor"));
      list.add(new DecNumber(buffer, offset + 4, 2, "Shield"));
      list.add(new DecNumber(buffer, offset + 6, 2, "Gloves"));
      list.add(new DecNumber(buffer, offset + 8, 2, "Left ring"));
      list.add(new DecNumber(buffer, offset + 10, 2, "Right ring"));
      list.add(new DecNumber(buffer, offset + 12, 2, "Amulet"));
      list.add(new DecNumber(buffer, offset + 14, 2, "Belt"));
      list.add(new DecNumber(buffer, offset + 16, 2, "Boots"));
      list.add(new DecNumber(buffer, offset + 18, 2, "Weapon 1"));
      list.add(new DecNumber(buffer, offset + 20, 2, "Weapon 2"));
      list.add(new DecNumber(buffer, offset + 22, 2, "Weapon 3"));
      list.add(new DecNumber(buffer, offset + 24, 2, "Weapon 4"));
      list.add(new DecNumber(buffer, offset + 26, 2, "Quiver 1"));
      list.add(new DecNumber(buffer, offset + 28, 2, "Quiver 2"));
      list.add(new DecNumber(buffer, offset + 30, 2, "Quiver 3"));
      list.add(new DecNumber(buffer, offset + 32, 2, "Quiver 4"));
      list.add(new DecNumber(buffer, offset + 34, 2, "Cloak"));
      list.add(new DecNumber(buffer, offset + 36, 2, "Quick item 1"));
      list.add(new DecNumber(buffer, offset + 38, 2, "Quick item 2"));
      list.add(new DecNumber(buffer, offset + 40, 2, "Quick item 3"));
      list.add(new DecNumber(buffer, offset + 42, 2, "Inventory 1"));
      list.add(new DecNumber(buffer, offset + 44, 2, "Inventory 2"));
      list.add(new DecNumber(buffer, offset + 46, 2, "Inventory 3"));
      list.add(new DecNumber(buffer, offset + 48, 2, "Inventory 4"));
      list.add(new DecNumber(buffer, offset + 50, 2, "Inventory 5"));
      list.add(new DecNumber(buffer, offset + 52, 2, "Inventory 6"));
      list.add(new DecNumber(buffer, offset + 54, 2, "Inventory 7"));
      list.add(new DecNumber(buffer, offset + 56, 2, "Inventory 8"));
      list.add(new DecNumber(buffer, offset + 58, 2, "Inventory 9"));
      list.add(new DecNumber(buffer, offset + 60, 2, "Inventory 10"));
      list.add(new DecNumber(buffer, offset + 62, 2, "Inventory 11"));
      list.add(new DecNumber(buffer, offset + 64, 2, "Inventory 12"));
      list.add(new DecNumber(buffer, offset + 66, 2, "Inventory 13"));
      list.add(new DecNumber(buffer, offset + 68, 2, "Inventory 14"));
      list.add(new DecNumber(buffer, offset + 70, 2, "Inventory 15"));
      list.add(new DecNumber(buffer, offset + 72, 2, "Inventory 16"));
      list.add(new DecNumber(buffer, offset + 74, 2, "Magically created weapon"));
      list.add(new DecNumber(buffer, offset + 76, 2, "Weapon slot selected"));
      list.add(new DecNumber(buffer, offset + 78, 2, "Weapon ability selected"));
    }
    int endoffset = offset;
    for (int i = 0; i < list.size(); i++) {
      StructEntry entry = list.get(i);
      if (entry.getOffset() + entry.getSize() > endoffset)
        endoffset = entry.getOffset() + entry.getSize();
    }
    return endoffset;
  }

  private void updateMemorizedSpells()
  {
    // Assumes memorized spells offset is correct
    int offset = ((HexNumber)getAttribute("Memorized spells offset")).getValue() + getExtraOffset();
    int count = 0;
    for (int i = 0; i < list.size(); i++) {
      Object o = list.get(i);
      if (o instanceof SpellMemorization) {
        SpellMemorization info = (SpellMemorization)o;
        int numSpells = info.updateSpells(offset, count);
        offset += 12 * numSpells;
        count += numSpells;
      }
    }
    ((DecNumber)getAttribute("# memorized spells")).setValue(count);
  }

  private void updateOffsets(AddRemovable datatype, int size)
  {
    if (getStructEntryAt(0).toString().equalsIgnoreCase("CHR "))
      ((HexNumber)getAttribute("CRE structure length")).incValue(size);
    if (!(datatype instanceof MemorizedSpells)) {
      HexNumber offsetMemSpells = (HexNumber)getAttribute("Memorized spells offset");
      if (datatype.getOffset() < offsetMemSpells.getValue() + getExtraOffset() ||
          datatype.getOffset() == offsetMemSpells.getValue() + getExtraOffset() && size > 0)
        offsetMemSpells.incValue(size);
    }
  }
}
