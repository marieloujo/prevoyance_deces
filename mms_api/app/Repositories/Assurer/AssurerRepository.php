<?php

namespace App\Repositories\Assurer;

use App\Models\Assurer;
use App\Repositories\Assurer\Interfaces\AssurerRepositoryInterface;

class AssurerRepository implements AssurerRepositoryInterface
{
    protected $assurer;

    public function __construct(Assurer $assurer)
    {
        $this->assurer = $assurer;
    }
    /**
     * Get a assure by it's ID
     *
     * @param int
     * @return collection
     */

    public function getById($id)
    {
        return $this->assurer->findOrfail($id);
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
        return $this->assurer->paginate();
    }

    /**
     * Delete a assure.
     *
     * @param int
     */
    public function delete($id)
    {
        $this->Assurer->findOrfail($id)->delete();
        
    }

    /**
     * Register a assurer.
     *
     * @param array
     */
    public function create(array $assurer_data)
    {
            $assurer = new Assurer();
            $assurer->employeur = $assurer_data['employeur'];
            $assurer->profession = $assurer_data['profession'];
            $assurer->etat = $assurer_data['etat'];
            $assurer->save();

            return $assurer;
    }

    /**
     * Update a assurer.
     *
     * @param int
     * @param array
     */
    public function update($id, array $assurer_data)
    {
            $assurer =  $this->assurer->findOrfail($id);
            $assurer->employeur = $assurer_data['employeur'];
            $assurer->profession = $assurer_data['profession'];
            $assurer->etat = $assurer_data['etat'];
            $assurer->update();
    }

}

