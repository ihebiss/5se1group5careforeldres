package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.Entity.Medicament;
import com.example.carecareforeldres.Entity.Ordonnance;
import com.example.carecareforeldres.Repository.MedicamentRepository;
import com.example.carecareforeldres.Repository.OrdenanceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class MedicamentService  implements IServiceMedicament{
 MedicamentRepository medicamentRepository;

    private OrdenanceRepository ordonnanceRepository;

    private EntityManager entityManager;


    public List<Medicament> findTop3MedicamentsConsumedBetweenDates(LocalDate startDate, LocalDate endDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Medicament> query = criteriaBuilder.createQuery(Medicament.class);
        Root<Ordonnance> ordonnanceRoot = query.from(Ordonnance.class);

        Join<Ordonnance, Medicament> medicamentJoin = ordonnanceRoot.join("medicaments", JoinType.INNER);
        query.select(medicamentJoin);

        query.where(
                criteriaBuilder.between(ordonnanceRoot.get("dateAjout"), startDate, endDate)
        );

        query.groupBy(medicamentJoin.get("idMedicament"));
        query.orderBy(criteriaBuilder.desc(criteriaBuilder.count(medicamentJoin)));

        TypedQuery<Medicament> typedQuery = entityManager.createQuery(query);
        typedQuery.setMaxResults(3);

        return typedQuery.getResultList();
    }



    @Override
    public Medicament addMed(Medicament med) {
        LocalDate d=LocalDate.now();
        med.setDateMed(d);
        return medicamentRepository.save(med);}
    @Override
    public List<Medicament> getAllMed(){return medicamentRepository.findAll();}

    @Override
    public void remove(int idf) {
        medicamentRepository.deleteMedicamentOrdonnance(idf);
        medicamentRepository.deleteById(idf);}

    @Override
    public Medicament update(Medicament med) {

        return medicamentRepository.save(med);
    }




}
