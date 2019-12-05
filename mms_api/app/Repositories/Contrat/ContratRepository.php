<?php

namespace App\Repositories\contrat;

use App\Models\Contrat;
use App\Repositories\Contrat\Interfaces\ContratRepositoryInterface;
use Carbon\Carbon;

class ContratRepository implements ContratRepositoryInterface
{
    protected $contrat;

    public function __construct(Contrat $contrat)
    {
        $this->contrat = $contrat;
    }
    /**
     * Get a contrat by it's ID
     *
     * @param int
     * @return collection
     */

    public function getById($id)
    {
        return $this->contrat->findOrfail($id);
    }

    /**
     * Get a contrat by it's ID
     *
     * @param string
     * @return collection
     */

    /**
     * Get's all assurés.
     *
     * @return mixed
     */
    public function all($user)
    {
        return Client::findOrfail($user)->contrats;
    }

    /**
     * Delete a contrat.
     *
     * @param int
     */
    public function delete($id)
    {
        $this->contrat->findOrfail($id)->delete();
        
    }

    /**
     * Register a contrat.
     *
     * @param array
     */
    public function create(array $contrat_data)
    {
            $contrat = new Contrat();

            $contrat->numero_contrat = $contrat_data['numero_contrat'];
            $contrat->garantie = $contrat_data['garantie'];
            $contrat->prime = $contrat_data['prime'];
            $contrat->duree = $contrat_data['duree'];
            $contrat->date_debut = $contrat_data['date_debut'];
            $contrat->date_echeance = $contrat_data['date_echeance'];
            $contrat->date_effet = $contrat_data['date_effet'];
            $contrat->date_fin = $contrat_data['date_fin'];
            $contrat->fin = false;
            $contrat->valider = false;
            $contrat->client_id = $contrat_data['client_id'];
            $contrat->marchand_id = $contrat_data['marchand_id'];
            $contrat->assure_id = $contrat_data['assure_id'];
            $contrat->numero_police_assurance = $contrat_data['numero_police_assurance'];
            $contrat->save();

            return $contrat;
    }

    /**
     * Update a contrat.
     *
     * @param int
     * @param array
     */
    public function update($id, array $contrat_data)
    {
            $contrat =  $this->contrat->findOrfail($id);

            $contrat->numero_contrat = $contrat_data['numero_contrat'];
            $contrat->garantie = $contrat_data['garantie'];
            $contrat->prime = $contrat_data['prime'];
            $contrat->portefeuille = $contrat_data['portefeuille'];
            $contrat->date_debut = $contrat_data['date_debut'];
            $contrat->date_echeance = $contrat_data['date_echeance'];
            $contrat->date_effet = $contrat_data['date_effet'];
            $contrat->fin = $contrat_data['date_fin'];
            $contrat->fin = $contrat_data['fin'];
            $contrat->fin = $contrat_data['valider'];
            $contrat->client_id = $contrat_data['client_id'];
            $contrat->marchand_id = $contrat_data['marchand_id'];
            $contrat->assure_id = $contrat_data['assure_id'];
            $contrat->numero_police_assurance = $contrat_data['numero_police_assurance'];

            $contrat->update();
    }
    public function getByNumeroContrat($id){
        return $this->contrat->where('numero_contrat',$id)->first();
    }
}

