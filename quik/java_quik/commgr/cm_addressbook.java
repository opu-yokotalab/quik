package java_quik.commgr;
import java_quik.*;
import java_quik.gui.*;

public class CM_AddressBook {
  String MediName,
         ATPLoc,
         FileLoc;
  CM_AddressBook next;

  public CM_AddressBook( String mname, String aname, String fname,
                         CM_AddressBook ptr) {
    MediName = mname;
    ATPLoc = aname;
    FileLoc = fname;
    next = ptr;
  }

  public CM_AddressBook( String mname, String aname, String fname ) {
    this( mname, aname, fname, null);
  }

  public CM_AddressBook() {
    this( null, null, null, null);
  }

  public void connect( CM_AddressBook ptr ) {
    next = ptr;
  }

  public boolean searchMediName( String Name ) {
    return MediName.equals(Name);
  }

  public String getFile() {
    return FileLoc;
  }

  public String getATP() {
    return ATPLoc;
  }

  public String getMediName() {
    return MediName;
  }
}
