package fr.projet.manga_up.service;

import fr.projet.manga_up.controller.GenreController;
import fr.projet.manga_up.dao.GenderDao;
import fr.projet.manga_up.dao.UserDao;
import fr.projet.manga_up.dto.AddressDto;
import fr.projet.manga_up.dto.GenderDto;
import fr.projet.manga_up.mapper.GenderMapper;
import fr.projet.manga_up.model.Address;
import fr.projet.manga_up.model.Gender;
import fr.projet.manga_up.model.Manga;
import fr.projet.manga_up.model.AppUser;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenreController.class);

    @Autowired
    private GenderDao genderDao;
    @Autowired
    private GenderMapper genderMapper;
    @Autowired
    private UserDao userDao;


    /**
     * Récupère une page paginée de genres.
     *
     * <p>Cette méthode utilise un objet {@link Pageable} pour définir les paramètres de pagination et de tri des résultats.
     * Elle appelle le DAO pour obtenir une page de genres en fonction de ces paramètres.
     *
     * <p>La méthode est conçue pour fournir une liste paginée de genres, facilitant ainsi la gestion des grandes quantités
     * de données en les divisant en pages plus petites.
     *
     * @param pageable un objet {@link Pageable} contenant les informations de pagination (numéro de la page, taille de la page)
     *                et de tri des résultats
     * @return une page de genres {@link Page<Gender>} correspondant aux paramètres de pagination fournis
     */
    public Page<Gender> getAllGenders(Pageable pageable)
    {
        return genderDao.findAll(pageable);
    }



    public List<Gender> getAllGendersList() {
        return genderDao.findAllList();
    }



    /**
     * Récupère un genre par son identifiant.
     *
     * <p>Cette méthode cherche un genre dans la base de données en utilisant son identifiant.
     * Si le genre est trouvé, il est retourné. Sinon, une exception {@link ResponseStatusException}
     * avec un statut HTTP 404 (NOT_FOUND) est levée pour indiquer que le genre n'a pas été trouvé.
     *
     *
     * @param id l'identifiant unique du genre à récupérer
     * @return l'objet {@link Gender} correspondant à l'ID fourni
     * @throws ResponseStatusException si le genre avec l'ID donné n'est pas trouvé
     */
    public Gender getGender(Integer id){
        Optional<Gender> mo=genderDao.findById(id);
        if(mo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun Genre n'a été trouvé");
        }else {
            return mo.get();
        }
    }



    /**
     * Crée un nouveau genre à partir d'un DTO.
     *
     * <p>Cette méthode convertit un objet {@link GenderDto} en une entité {@link Gender}, puis sauvegarde
     * l'entité dans la base de données via le DAO. L'entité sauvegardée est ensuite convertie à nouveau en DTO
     * et renvoyée.
     *
     * <p>La méthode est annotée avec {@link Transactional}, ce qui garantit que la création du genre est
     * effectuée dans une transaction. Si une erreur survient, la transaction est annulée.
     *
     * @param genderDto un objet {@link GenderDto} contenant les informations du genre à créer
     * @return un objet {@link GenderDto} représentant le genre créé
     */
    @Transactional
    public GenderDto saveGender(GenderDto genderDto) {
        Gender gender = genderMapper.toEntity(genderDto);
        gender = genderDao.save(gender);
        return genderMapper.toDto(gender);
    }



    @Transactional
    public void deleteGender(int genderId) {
        Gender gender = genderDao.findById(genderId)
                .orElseThrow(()-> new EntityNotFoundException("Le genre utilisateur n'existe pas"));
        for(AppUser user : gender.getUsers() ){
            user.setGender(null);
            genderDao.save(gender);
        }
        genderDao.deleteById(genderId);
    }


    /**
     * Met à jour les informations d'un genre existant.
     *
     * <p>Cette méthode recherche un genre dans la base de données en utilisant son identifiant.
     * Si le genre est trouvé, ses attributs sont mis à jour avec les valeurs fournies dans l'objet
     * {@link GenderDto}. La catégorie mise à jour est ensuite sauvegardée dans la base de données
     * et convertie à nouveau en DTO avant d'être renvoyée.
     *
     * <p>La méthode est annotée avec {@link Transactional}, ce qui garantit que la mise à jour du genre
     * est effectuée dans une transaction. Si une erreur survient, la transaction est annulée.
     *
     * <p>Un message de log est enregistré pour indiquer que la mise à jour du genre a été effectuée.
     *
     * @param id l'identifiant unique du genre à mettre à jour
     * @param genderDto un objet {@link GenderDto} contenant les nouvelles valeurs des attributs du genre
     * @return un objet {@link GenderDto} représentant le genre mis à jour
     * @throws EntityNotFoundException si le genre avec l'ID donné n'est pas trouvé
     */
    @Transactional
    public GenderDto updateGender(Integer id, GenderDto genderDto) {
        // Trouve le genre d'utilisateur existant
        Gender gender = genderDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gender not found"));
        gender.setLabel(genderDto.getLabel());

        gender = genderDao.save(gender);
        LOGGER.info("updateGender");
        return genderMapper.toDto(gender);
    }

    /**
     * Récupère tous les genres sous forme de DTO.
     *
     * <p>Cette méthode récupère toutes les entités {@link Gender} depuis la base de données en utilisant
     * la méthode {@link GenderDao#findAll()}. Les entités récupérées sont ensuite converties en objets
     * {@link GenderDto} à l'aide du {@link GenderMapper} et collectées dans un ensemble.
     *
     * <p>Un message de log est enregistré pour indiquer que la récupération des genres a été effectuée.
     *
     * @return un ensemble de {@link GenderDto} représentant tous les genres disponibles dans la base de données
     */
    public Set<GenderDto> getAllGenderDto() {
        LOGGER.info("getAllGenderDto");
        List<Gender> genderList = new ArrayList<Gender>();
        genderDao.findAll().forEach(genderList::add);

        return genderList.stream()
                .map(genderMapper::toDto)
                .collect(Collectors.toSet());
    }





}
