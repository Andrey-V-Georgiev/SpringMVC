package realestate.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import realestate.domain.models.view.OfferViewModel;
import realestate.services.HomeService;
import realestate.utils.HtmlFileReader;

import java.io.IOException;
import java.math.RoundingMode;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private HtmlFileReader htmlFileReader;
    private final String INDEX_PAGE_FILE_PATH = "C:\\JAVA_PROJECTS\\SpringMVC\\_02_ExSpringBootIntro\\src\\main\\resources\\static\\index.html";
    private HomeService homeService;
    private ModelMapper modelMapper;

    @Autowired
    public HomeController(HtmlFileReader htmlFileReader, HomeService homeService, ModelMapper modelMapper) {
        this.htmlFileReader = htmlFileReader;
        this.homeService = homeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @ResponseBody
    public String index() throws IOException {

        String indexPage = this.htmlFileReader.readHtmlFile(INDEX_PAGE_FILE_PATH);
        StringJoiner sj = new StringJoiner("");

        List<OfferViewModel> allOffers = this.homeService.findAllOffers()
                .stream()
                .map(m -> this.modelMapper.map(m, OfferViewModel.class))
                .collect(Collectors.toList());
        if (allOffers.size() != 0) {
            for (OfferViewModel offer : allOffers) {
                sj.add("<div class=\"apartment\">");

                String rent = offer.getApartmentRent().setScale(2, RoundingMode.CEILING).toString();
                sj.add(String.format("<p>Rent: %s</p>", rent));

                String type = offer.getApartmentType();
                sj.add(String.format("<p>Type: %s</p>", type));

                String commission = offer.getAgencyCommission().setScale(2, RoundingMode.CEILING).toString();
                sj.add(String.format("<p>Commission: %s</p>", commission));

                sj.add("</div>");
            }
            return indexPage.replace("{{offers}}", sj.toString());
        } else {
            sj.add("<div class=\"no-offers\">");
            sj.add("<p>There aren't any offers!</p>");
            sj.add("</div>");
            return indexPage.replace("{{offers}}", sj.toString());
        }
    }
}
