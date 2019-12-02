<?php

namespace App\Repositories\Beneficiaire;

use App\Models\Beneficiaire;
use App\Repositories\Beneficiaire\Interfaces\BeneficiaireRepositoryInterface;

class BeneficiaireRepository implements BeneficiaireRepositoryInterface
{
    protected $beneficiaire;

    public function __construct(Beneficiaire $beneficiaire)
    {
        $this->beneficiaire = $beneficiaire;
    }
    /**
     * Get a beneficiaire by it's ID
     *
     * @param int
     * @return collection
     */

    public function getById($id)
    {
        return $this->beneficiaire->findOrfail($id);
    }

    /**
     * Get a beneficiaire by it's ID
     *
     * @param string
     * @return collection
     */

    /**
     * Get's all assurés.
     *
     * @return mixed
     */
    public function all()
    {
        return $this->beneficiaire->paginate();
    }

    /**
     * Delete a beneficiaire.
     *
     * @param int
     */
    public function delete($id)
    {
        $this->beneficiaire->findOrfail($id)->delete();
        
    }

    /**
     * Register a beneficiaire.
     *
     * @param array
     */
    public function create(array $beneficiaire_data)
    {
            $beneficiaire = new Beneficiaire();
            $beneficiaire->save();

            return $beneficiaire;
    }

    /**
     * Update a beneficiaire.
     *
     * @param int
     * @param array
     */
    public function update($id, array $beneficiaire_data)
    {
            $beneficiaire =  $this->beneficiaire->findOrfail($id);
            $beneficiaire->update();
    }

}

