<?php

namespace App\Repositories\Assurer;

use App\Models\Assure;
use App\Repositories\Assurer\Interfaces\AssurerRepositoryInterface;

class AssurerRepository implements AssurerRepositoryInterface
{
    protected $assure;

    public function __construct(Assure $assure)
    {
        $this->assure = $assure;
    }
    /**
     * Get a assure by it's ID
     *
     * @param int
     * @return collection
     */

    public function getById($id)
    {
        return $this->assure->findOrfail($id);
    }

    /**
     * Get a assure by it's ID
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
        return $this->assure->paginate();
    }

    /**
     * Delete a assure.
     *
     * @param int
     */
    public function delete($id)
    {
        $this->assure->findOrfail($id)->delete();
        
    }

    /**
     * Register a assure.
     *
     * @param array
     */
    public function create(array $assure_data)
    {
            $assure = new Assure();
            $assure->employeur = $assure_data['employeur'];
            $assure->profession = $assure_data['profession'];
            $assure->etat = $assure_data['etat'];
            $assure->save();

            return $assure;
    }

    /**
     * Update a assurer.
     *
     * @param int
     * @param array
     */
    public function update($id, array $assure_data)
    {
            $assure =  $this->assure->findOrfail($id);
            $assure->employeur = $assure_data['employeur'];
            $assure->profession = $assure_data['profession'];
            $assure->etat = $assure_data['etat'];
            $assure->update();
    }

}

