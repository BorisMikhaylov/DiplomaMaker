class DiplomaImpl implements Diploma {
  private int ID;
  private String firstName;
  private String lastName;
  private String patronymic;
  private String school;
  private String subject;
  private int degree;

  @Override
  public String getFirstName() {
    return firstName;
  }

  @Override
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public String getLastName() {
    return lastName;
  }

  @Override
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String getPatronymic() {
    return patronymic;
  }

  @Override
  public void setPatronymic(String patronymic) {
    this.patronymic = patronymic;
  }

  @Override
  public String getSchool() {
    return school;
  }

  @Override
  public void setSchool(String school) {
    this.school = school;
  }

  @Override
  public String getSubject() {
    return subject;
  }

  @Override
  public void setSubject(String subject) {
    this.subject = subject;
  }

  @Override
  public int getDegree() {
    return degree;
  }

  @Override
  public void setDegree(int degree) {
    this.degree = degree;
  }

  @Override
  public void setID(int numericCellValue) {
    this.ID = numericCellValue;
  }

  @Override
  public String toString() {
    return "ID: " + ID + ",\n" +
        "Имя: " + firstName + ",\n" +
        "Фамилия: " + lastName + ",\n" +
        "Отчество: " + patronymic + ",\n" +
        "Школа: " + school + ",\n" +
        "Предмет: " + subject + ",\n" +
        "Степень: " + degree;
  }

  // Дополнительные геттеры и сеттеры
}