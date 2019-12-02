<?php

namespace App\Repositories\Direction;

use App\Models\Direction;
use App\Repositories\Direction\Interfaces\DirectionRepositoryInterface;

class DirectionRepository implements DirectionRepositoryInterface
{
    protected $direction;

    public function __construct(Direction $direction)
    {
        $this->direction = $direction;
    }
    /**
     * Get a direction by it's ID
     *
     * @param int
     * @return collection
     */

    public function getById($id)
    {
        return $this->direction->findOrfail($id);
    }

    /**
     * Get a direction by it's ID
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
        return $this->direction->paginate();
    }

    /**
     * Delete a direction.
     *
     * @param int
     */
    public function delete($id)
    {
        $this->direction->findOrfail($id)->delete();
        
    }

    /**
     * Register a direction.
     *
     * @param array
     */
    public function create(array $direction_data)
    {
            $direction = new Direction();
            $direction->role_id = $direction_data['role_id'];
            $direction->save();

            return $direction;
    }

    /**
     * Update a direction.
     *
     * @param int
     * @param array
     */
    public function update($id, array $direction_data)
    {
            $direction =  $this->direction->findOrfail($id);
            $direction->update();
    }

}

