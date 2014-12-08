<%!
  static final String[] meinTextArray = {
    "aabbcc", "abc 1", "abc1", "abc2", "ajax1", "ajax2", "xyzax", "xyzäx", "xyzbx", "xyzzx"
  };
  boolean sorted = false;
%>
<%
  response.setHeader("Cache-Control", "no-cache");
  String eingabe = request.getParameter( "eingabe" );
  if( null != eingabe && 0 < eingabe.trim().length() ) {
    if( !sorted ) {
      java.util.Arrays.sort( meinTextArray );
      sorted = true;
    }
    StringBuffer auswahl = new StringBuffer();
    boolean resultFound = false;
    for( int i=0; i<meinTextArray.length; i++ ) {
      if( meinTextArray[i].startsWith( eingabe ) ) {
        auswahl.append( meinTextArray[i] ).append( ";" );
        resultFound = true;
      } else {
        if( resultFound ) break;
      }
    }
    if( 0 < auswahl.length() ) {
      auswahl.setLength( auswahl.length() - 1 );
    }
    out.println( auswahl.toString() );
  }
%>