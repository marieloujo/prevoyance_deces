<?php

namespace App\Repositories\Benefice;

use App\Models\Benefice;
use App\Repositories\Benefice\Interfaces\BeneficeRepositoryInterface;

class BeneficeRepository implements BeneficeRepositoryInterface
{
    protected $benefice;

    public function __construct(Benefice $benefice)
    {
        $this->benefice = $benefice;
    }
    /**
     * Get a benefice by it's ID
     *
     * @param int
     * @return collection
     */

    public function getById($id)
    {
        return $this->benefice->findOrfail($id);
    }

    /**
     * Get a benefice by it's ID
     *
     * @param string
     * @return collection
     */

    /**
     * Get's all benefices.
     *
     * @return mixed
     */
    public function all()
    {
        return $this->benefice->paginate();
    }

    /**
     * Delete a benefice.
     *
     * @param int
     */
    public function delete($id)
    {
        $this->benefice->findOrfail($id)->delete();
        
    }

    /**
     * Register a benefice.
     *
     * @param array
     */
    public function create(array $benefice_data)
    {
            $benefice = new Benefice();
            $benefice->statut = $benefice_data['statut'];
            $benefice->taux = $benefice_data['taux'];
            $benefice->beneficiaire_id = $benefice_data['beneficiaire_id'];
            $benefice->contrat_id = $benefice_data['contrat_id'];
            $benefice->save();

            return $benefice;
    }

    /**
     * Update a benefice.
     *
     * @param int
     * @param array
     */
    public function update($id, array $benefice_data)
    {
            $benefice =  $this->benefice->findOrfail($id);
            $benefice->statut = $benefice_data['statut'];
            $benefice->taux = $benefice_data['taux'];
            $benefice->beneficiaire_id = $benefice_data['beneficiaire_id'];
            $benefice->contrat_id = $benefice_data['contrat_id'];
           $benefice->update();
    }

}

