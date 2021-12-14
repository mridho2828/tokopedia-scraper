# tokopedia-scraper

Scraper to get list of products from "handphone" section on [tokopedia](https://www.tokopedia.com/search?st=product&q=handphone).
The scraping process is adapted from [this](https://github.com/stevanusc6/tokopedia-scraper-selenium) project. 
Currently only supports chromedriver for UNIX-based OS.

## Requirement

- jdk 1.8
- Gradle
- Chrome Driver

## Run

1. Build gradle project using `./gradlew build`
2. Run gradle project using `./gradlew run`
3. Wait until the process finish and the data will be stored in `handphones.csv` file