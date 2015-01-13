package android.bobority;

/**
 * Created by CarlDemolder on 1/10/2015.
 */
public class User
{
    String FirstName;
    String userFirstName;
    String LastName;
    String userLastName;
    String birthDate;
    String userBirthDate;
    String birthMonth;
    String userBirthMonth;
    String birthYear;
    String userBirthYear;
    String zipcode;
    String userZipcode;
    String street1;
    String userStreet1;
    String street2;
    String userStreet2;
    String city;
    String userCity;
    String state;
    String userState;
    String userCardMonth;
    String userCardYear;
    String userCardNum;
    String usercvv2;

    public User(
            String firstName,
            String lastName,
            String birthDate,
            String street1,
            String street2,
            String city,
            String state,
            String zipcode)
    {

    }

    public String getFirstName()
    {
        FirstName=userFirstName;
        return FirstName;
    }
    public void readFirstName(String temp)
    {
        userFirstName=temp;
    }

    public String getLastName()
    {
        return LastName;
    }
    public void readLastName(String temp)
    {
        userLastName=temp;
    }

    public String getBirthDate()
    {
        return birthDate;
    }
    public void readBirthDate(String temp)
    {
        userBirthDate=temp;
    }

    public String getBirthMonth()
    {
        return birthMonth;
    }
    public void readBirthMonth(String temp)
    {
        userBirthMonth=temp;
    }

    public String getBirthYear()
    {
        return birthYear;
    }
    public void readBirthYear(String temp)
    {
        userBirthYear=temp;
    }

    public String getStreet1()
    {
        return street1;
    }
    public void readStreet1(String temp)
    {
        userStreet1=temp;
    }

    public String getStreet2()
    {
        return street2;
    }
    public void readStreet2(String temp)
    {
        userStreet2=temp;
    }

    public String getCity()
    {
        return city;
    }
    public void readCity(String temp)
    {
        userCity=temp;
    }

    public String getState()
    {
        return state;
    }
    public void readState(String temp)
    {
        userState=temp;
    }

    public String getZipcode()
    {
        return zipcode;
    }
    public void readZipcode(String temp)
    {
        userZipcode=temp;
    }

    public String getCardMonth()
    {
        return userCardMonth;
    }
    public void readCardMonth(String temp)
    {
        userCardMonth=temp;
    }

    public String getCardYear()
    {
        return userCardYear;
    }
    public void readCardYear(String temp)
    {
        userCardYear=temp;
    }
    public String getCardNum()
    {
        return userCardNum;
    }
    public void readCardNum(String temp)
    {
        userCardNum=temp;
    }

    public String getcvv2()
    {
        return usercvv2;
    }
    public void readcvv2(String temp) {
        usercvv2 = temp;
    }
}


