<?php

namespace App\Repositories\User\Interfaces;

interface UserRepositoryInterface
{

    /**
     * Get's all users.
     *
     * @return mixed
     */
    public function all();

    /**
     * Logout user.
     *
     * @return mixed
     */
    public function logout();


    /**
     * Deletes an user.
     *
     * @param int
     */
    public function delete($id);

    /**
     * Get's an user by it's ID
     *
     * @param int
     */
    public function getById($id);

    /**
     * Get's an user by it's name
     *
     * @param string
     */
    public function getByName($name);

    /**
     * Login an user.
     *
     * @param array
     */
    public function login(array $user_data);

    /**
     * Register an user.
     *
     * @param array
     */
    public function register(array $user_data);

    /**
     * Update an user.
     *
     * @param int
     * @param array
     */
    public function update($id, array $user_data);


}