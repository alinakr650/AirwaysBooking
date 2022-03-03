package com.tickets.booking.domain.nationalities;

public enum Nationality {
    Afghanistan,
    Albania,
    Algeria,
    Andorra,
    Angola,
    Antigua_and_Barbuda("Antigua and Barbuda"),
    Argentina,
    Armenia,
    Australia,
    Austria,
    Azerbaijan,
    Bahamas,
    Bahrain,
    Bangladesh,
    Barbados,
    Belarus,
    Belgium,
    Belize,
    Benin,
    Bhutan,
    Bolivia,
    Bosnia_and_Herzegovina("Bosnia and Herzegovina"),
    Botswana,
    Brazil,
    Brunei,
    Bulgaria,
    Burkina_Faso("Burkina Faso"),
    Burma,
    Burundi,
    Cabo_Verde("Cabo Verde"),
    Cambodia,
    Cameroon,
    Canada,
    Central_African_Republic("Central African Republic"),
    Chad,
    Chile,
    China,
    Colombia,
    Comoros,
    Congo,
    Costa_Rica("Costa Rica"),
    Côte_dIvoire("Cote d'Ivoire"),
    Croatia,
    Cuba,
    Cyprus,
    Czech_Republic("Czech Republic"),
    Denmark,
    Djibouti,
    Dominica,
    Dominican_Republic("Dominican Republic"),
    East_Timor("East Timor"),
    Ecuador,
    Egypt,
    El_Salvador("El Salvador"),
    Equatorial_Guinea("Equatorial Guinea"),
    Eritrea,
    Estonia,
    Ethiopia,
    Fiji,
    Finland,
    France,
    Gabon,
    Gambia,
    Georgia,
    Germany,
    Ghana,
    Gibraltar,
    Greece,
    Grenada,
    Guatemala,
    Guinea,
    Guinea_Bissau("Guinea-Bissau"),
    Guyana,
    Haiti,
    Honduras,
    Hungary,
    Iceland,
    India,
    Indonesia,
    Iran,
    Iraq,
    Ireland,
    Israel,
    Italy,
    Ivory_Coast("Ivory Coast"),
    Jamaica,
    Japan,
    Jordan,
    Kazakhstan,
    Kenya,
    Kiribati,
    North_Korea("North Korea"),
    South_Korea("South Korea"),
    Kuwait,
    Kyrgyzstan,
    Laos,
    Latvia,
    Lebanon,
    Lesotho,
    Liberia,
    Libya,
    Liechtenstein,
    Lithuania,
    Luxembourg,
    Macedonia,
    Madagascar,
    Malawi,
    Malaysia,
    Maldives,
    Mali,
    Malta,
    Marshall,
    Martinique,
    Mauritania,
    Mauritius,
    Mexico,
    Micronesia,
    Moldova,
    Monaco,
    Mongolia,
    Montenegro,
    Morocco,
    Mozambique,
    Namibia,
    Nauru,
    Nepal,
    Netherlands,
    New_Zealand("New Zeland"),
    Nicaragua,
    Niger,
    Nigeria,
    Northern_Mariana_Islands("Northern Mariana Islands"),
    Norway,
    Oman,
    Pakistan,
    Palau,
    Palestine,
    Panama,
    Papua_New_Guinea("Papua New Guinea"),
    Paraguay,
    Peru,
    Philippines,
    Poland,
    Portugal,
    Puerto_Rico("Puerto Rico"),
    Qatar,
    Romania,
    Russia,
    Rwanda,
    Saint_Kitts_and_Nevis("Saint Kitts and Nevis"),
    Saint_Lucia("Saint Lucia"),
    Saint_Vincent_and_the_Grenadines("Saint Vincent and the Grenadines"),
    Samoa,
    San_Marino("San Marino"),
    São_Tomé_and_Príncipe("São Tomé and Príncipe"),
    Saudi_Arabia("Saudi Arabia"),
    Senegal,
    Serbia,
    Seychelles,
    Sierra_Leone("Sierra Leone"),
    Singapore,
    Slovakia,
    Slovenia,
    Solomon_Islands("Solomon Islands"),
    Somalia,
    South_Africa("South Africa"),
    South_Sudan("South Sudan"),
    Spain,
    Sri_Lanka,
    Sudan,
    Suriname,
    Swaziland,
    Sweden,
    Switzerland,
    Syria,
    Tajikistan,
    Tanzania,
    Thailand,
    Timor_Leste("Timor-Leste"),
    Togo,
    Tokelau,
    Tonga,
    Trinidad_and_Tobago("Trinidad and Tobago"),
    Tunisia,
    Turkey,
    Turkmenistan,
    Tuvalu,
    Uganda,
    Ukraine,
    UAE,
    UK,
    USA,
    Uruguay,
    Uzbekistan,
    Vanuatu,
    Vatican_City("Vatican City"),
    Venezuela,
    Vietnam,
    Yemen,
    Zambia,
    Zimbabwe;

    Nationality(){
    }

    Nationality(String name){
    }

    public static String getValue(String name){
        return valueOf(name).toString();
    }
}
