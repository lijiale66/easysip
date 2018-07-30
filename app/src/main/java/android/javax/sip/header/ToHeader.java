/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Unpublished - rights reserved under the Copyright Laws of the United States.
 * Copyright � 2003 Sun Microsystems, Inc. All rights reserved.
 * Copyright � 2005 BEA Systems, Inc. All rights reserved.
 *
 * Use is subject to license terms.
 *
 * This distribution may include materials developed by third parties.
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *
 * Module Name   : JSIP Specification
 * File Name     : ToHeader.java
 * Author        : Phelim O'Doherty
 *
 *  HISTORY
 *  Version   Date      Author              Comments
 *  1.1     08/10/2002  Phelim O'Doherty
 *          12/15/2004  M. Ranganathan      Added clarification for To header setTag()
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package android.javax.sip.header;

import java.text.ParseException;


/**
 * The To header field first and foremost specifies the desired "logical"
 * recipient of the request, or the address-of-record of the user or resource
 * that is the target of this request.  This may or may not be the ultimate
 * recipient of the request. Requests and Responses must contain a ToHeader,
 * indicating the desired recipient of the Request. The UAS or redirect server
 * copies the ToHeader into its Response.
 * <p>
 * The To header field MAY contain a SIP or SIPS URI, but it may also make use
 * of other URI schemes i.e the telURL, when appropriate. All SIP
 * implementations MUST support the SIP URI scheme.  Any implementation that
 * supports TLS MUST support the SIPS URI scheme. Like the From header field,
 * it contains a URI and optionally a display name, encapsulated in a
 * {@link android.javax.sip.address.Address}.
 * <p>
 * A UAC may learn how to populate the To header field for a particular request
 * in a number of ways.  Usually the user will suggest the To header field
 * through a human interface, perhaps inputting the URI manually or selecting
 * it from some sort of address book.  Using the string to form the user part
 * of a SIP URI implies that the User Agent wishes the name to be resolved in the
 * domain to the right-hand side (RHS) of the at-sign in the SIP URI.  Using
 * the string to form the user part of a SIPS URI implies that the User Agent wishes to
 * communicate securely, and that the name is to be resolved in the domain to
 * the RHS of the at-sign. The RHS will frequently be the home domain of the
 * requestor, which allows for the home domain to process the outgoing request.
 * This is useful for features like "speed dial" that require interpretation of
 * the user part in the home domain.
 * <p>
 * The telURL may be used when the User Agent does not wish to specify the domain that
 * should interpret a telephone number that has been input by the user. Rather,
 * each domain through which the request passes would be given that opportunity.
 * As an example, a user in an airport might log in and send requests through
 * an outbound proxy in the airport.  If they enter "411" (this is the phone
 * number for local directory assistance in the United States), that needs to
 * be interpreted and processed by the outbound proxy in the airport, not the
 * user's home domain.  In this case, tel:411 would be the right choice.
 * <p>
 * Two To header fields are equivalent if their URIs match, and their
 * parameters match. Extension parameters in one header field, not present in
 * the other are ignored for the purposes of comparison. This means that the
 * display name and presence or absence of angle brackets do not affect
 * matching.
 * <ul>
 * <li> The "Tag" parameter - is used in the To and From header fields of SIP
 * messages.  It serves as a general mechanism to identify a dialog, which is
 * the combination of the Call-ID along with two tags, one from each
 * participant in the dialog.  When a UA sends a request outside of a dialog,
 * it contains a From tag only, providing "half" of the dialog ID. The dialog
 * is completed from the response(s), each of which contributes the second half
 * in the To header field. When a tag is generated by a UA for insertion into
 * a request or response, it MUST be globally unique and cryptographically
 * random with at least 32 bits of randomness. Besides the requirement for
 * global uniqueness, the algorithm for generating a tag is implementation
 * specific.  Tags are helpful in fault tolerant systems, where a dialog is to
 * be recovered on an alternate server after a failure.  A UAS can select the
 * tag in such a way that a backup can recognize a request as part of a dialog
 * on the failed server, and therefore determine that it should attempt to
 * recover the dialog and any other state associated with it.
 * </ul>
 * A request outside of a dialog MUST NOT contain a To tag; the tag in the To
 * field of a request identifies the peer of the dialog.  Since no dialog is
 * established, no tag is present.
 * <p>
 * For Example:<br>
 * <code>To: Carol sip:carol@jcp.org<br>
 * To: Duke sip:duke@jcp.org;tag=287447</code>
 *
 * @see HeaderAddress
 * @author BEA Systems, NIST
 * @version 1.2
 */
public interface ToHeader extends HeaderAddress, Parameters, Header {

    /**
     * Sets the tag parameter of the ToHeader. The tag in the To field of a
     * request identifies the peer of the dialog. If no dialog is established,
     * no tag is present.
     * <p>
     * The To Header MUST contain a new "tag" parameter. When acting as a UAC
     * the To "tag" is maintained by the SipProvider from the dialog layer,
     * however whan acting as a UAS the To "tag" is assigned by the application.
     * That is the tag assignment for outbound responses for messages in a
     * dialog is only the responsibility of the application for the first
     * outbound response. If AUTOMATIC_DIALOG_SUPPORT is set to <it>on</it>
     * (default behavior) then, after dialog establishment, the stack will take care
     * of the tag assignment. Null is acceptable as a tag value. Supplying null
     * for the tag results in a header without a tag.
     *
     * @param tag - the new tag of the To Header
     * @throws ParseException which signals that an error has been reached
     * unexpectedly while parsing the Tag value.
     */
    public void setTag(String tag) throws ParseException;

    /**
     * Gets tag of ToHeader. The Tag parameter identified the Peer of the
     * dialogue.
     *
     * @return the tag parameter of the ToHeader. Returns null if no Tag is
     * present, i.e no dialogue is established.
     */
    public String getTag();



    /**
     * Compare this ToHeader for equality with another. This method
     * overrides the equals method in android.javax.sip.Header. This method specifies
     * object equality as outlined by
     * <a href = "http://www.ietf.org/rfc/rfc3261.txt">RFC3261</a>.
     * Two To header fields are equivalent if their URIs match, and their
     * parameters match. Extension parameters in one header field, not present
     * in the other are ignored for the purposes of comparison. This means that
     * the display name and presence or absence of angle brackets do not affect
     * matching. When comparing header fields, field names are always
     * case-insensitive. Unless otherwise stated in the definition of a
     * particular header field, field values, parameter names, and parameter
     * values are case-insensitive. Tokens are always case-insensitive. Unless
     * specified otherwise, values expressed as quoted strings are case-sensitive.
     *
     * @param obj the object to compare this ToHeader with.
     * @return <code>true</code> if <code>obj</code> is an instance of this class
     * representing the same ToHeader as this, <code>false</code> otherwise.
     * @since v1.2
     */
    public boolean equals(Object obj);


    /**
     * Name of the ToHeader
     */
    public final static String NAME = "To";
}

